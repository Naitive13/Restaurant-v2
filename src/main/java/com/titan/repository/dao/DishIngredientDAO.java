package com.titan.repository.dao;

import com.titan.repository.mapper.DishIngredientMapper;
import com.titan.repository.db.Datasource;
import com.titan.model.entities.DishIngredient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class DishIngredientDAO implements CrudDAO<DishIngredient> {
  private final Datasource datasource;
  private final DishIngredientMapper dishIngredientMapper;
  private final IngredientDAO ingredientDAO;

  @Override
  public List<DishIngredient> getAll(int page, int pageSize) {
    List<DishIngredient> dishIngredients = new ArrayList<>();
    String query = "SELECT dish_id, ingredient_id, quantity FROM dish_ingredient LIMIT ? OFFSET ?";

    try (Connection connection = this.datasource.getConnection();
        PreparedStatement st = connection.prepareStatement(query)) {
      st.setInt(1, pageSize);
      st.setInt(2, (page - 1) * pageSize);

      try (ResultSet rs = st.executeQuery()) {
        while (rs.next()) {
          DishIngredient dishIngredient = dishIngredientMapper.apply(rs);
          dishIngredients.add(dishIngredient);
        }
      }
      return dishIngredients;
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public DishIngredient getById(long id) {
    String query = "SELECT dish_id, ingredient_id, quantity FROM dish_ingredient WHERE id=?";

    try (Connection connection = this.datasource.getConnection();
        PreparedStatement st = connection.prepareStatement(query)) {
      st.setLong(1, id);

      try (ResultSet rs = st.executeQuery()) {
        if (rs.next()) {
          return dishIngredientMapper.apply(rs);
        } else {
          throw new RuntimeException("DishIngredient not found");
        }
      }
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public List<DishIngredient> saveAll(List<DishIngredient> ingredientToAdd) {
    List<DishIngredient> dishIngredients = new ArrayList<>();
    String query =
        "INSERT INTO dish_ingredient "
            + "(id, dish_id, ingredient_id,quantity) "
            + "VALUES (?,?,?,?) "
            + "ON CONFLICT (dish_id, ingredient_id) DO UPDATE "
            + "SET quantity=excluded.quantity "
            + "RETURNING dish_id, ingredient_id, quantity";

    try (Connection connection = this.datasource.getConnection();
        PreparedStatement st = connection.prepareStatement(query)) {

      ingredientToAdd.forEach(
          dishIngredient -> {
            try {
              st.setLong(1, dishIngredient.hashCode());
              st.setLong(2, dishIngredient.getDishId());
              st.setLong(3, dishIngredient.getIngredient().getIngredientId());
              st.setDouble(4, dishIngredient.getQuantity());

              ingredientDAO.saveAll(List.of(dishIngredient.getIngredient()));
              try (ResultSet rs = st.executeQuery()) {
                if (rs.next()) {
                  dishIngredients.add(dishIngredientMapper.apply(rs));
                }
              }
            } catch (SQLException e) {
              throw new RuntimeException(e);
            }
          });

      return dishIngredients;
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  public List<DishIngredient> getIngredientsForDish(long dishId) {
    List<DishIngredient> dishIngredients = new ArrayList<>();
    String query = "SELECT dish_id, ingredient_id, quantity FROM dish_ingredient WHERE dish_id=?";

    try (Connection connection = this.datasource.getConnection();
        PreparedStatement st = connection.prepareStatement(query)) {
      st.setLong(1, dishId);

      try (ResultSet rs = st.executeQuery()) {
        while (rs.next()) {
          DishIngredient dishIngredient = dishIngredientMapper.apply(rs);
          dishIngredients.add(dishIngredient);
        }
      }
      return dishIngredients;
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }
}
