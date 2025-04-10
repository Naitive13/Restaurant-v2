package com.titan.repository.dao;

import com.titan.repository.mapper.IngredientMapper;
import com.titan.repository.db.Datasource;
import com.titan.model.entities.Ingredient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class IngredientDAO implements CrudDAO<Ingredient> {
  private final Datasource datasource;
  private final IngredientMapper ingredientMapper;
  private final PriceDAO priceDAO;
  private final StockDAO stockDAO;

  @Override
  public List<Ingredient> getAll(int page, int pageSize) {
    List<Ingredient> ingredients = new ArrayList<>();
    String query =
        "SELECT ingredient_id, ingredient_name, unit, last_modified "
            + "FROM ingredient LIMIT ? OFFSET ?";

    try (Connection connection = this.datasource.getConnection();
        PreparedStatement st = connection.prepareStatement(query)) {
      st.setInt(1, pageSize);
      st.setInt(2, (page - 1) * pageSize);

      try (ResultSet rs = st.executeQuery()) {
        while (rs.next()) {
          ingredients.add(ingredientMapper.apply(rs));
        }
      }
      return ingredients;
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public Ingredient getById(long id) {
    String query =
        "SELECT ingredient_id, ingredient_name, unit, last_modified "
            + "FROM ingredient WHERE ingredient_id = ?";

    try (Connection connection = this.datasource.getConnection();
        PreparedStatement st = connection.prepareStatement(query)) {
      st.setLong(1, id);

      try (ResultSet rs = st.executeQuery()) {
        if (rs.next()) {
          return ingredientMapper.apply(rs);
        } else {
          throw new RuntimeException("ingredient not found");
        }
      }
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public List<Ingredient> saveAll(List<Ingredient> ingredientToAdd) {
    List<Ingredient> ingredients = new ArrayList<>();
    String query =
        "INSERT INTO ingredient "
            + "(ingredient_id, ingredient_name, unit, last_modified) "
            + "VALUES (?,?,?::unit_type,?) "
            + "ON CONFLICT (ingredient_id) DO UPDATE SET "
            + "ingredient_name=excluded.ingredient_name, "
            + "last_modified=excluded.last_modified "
            + "RETURNING ingredient_id, ingredient_name, unit, last_modified";

    try (Connection connection = this.datasource.getConnection();
        PreparedStatement st = connection.prepareStatement(query)) {

      ingredientToAdd.forEach(
          ingredient -> {
            try {
              st.setLong(1, ingredient.getIngredientId());
              st.setString(2, ingredient.getIngredientName());
              st.setString(3, ingredient.getUnit().toString());
              st.setTimestamp(4, Timestamp.valueOf(ingredient.getLastModified()));

              try (ResultSet rs = st.executeQuery()) {
                priceDAO.saveAll(ingredient.getIngredientPrices());
                stockDAO.saveAll(ingredient.getStockMovements());

                if (rs.next()) {
                  ingredients.add(ingredientMapper.apply(rs));
                }
              }

            } catch (SQLException e) {
              throw new RuntimeException(e);
            }
          });


      return ingredients;
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }
}
