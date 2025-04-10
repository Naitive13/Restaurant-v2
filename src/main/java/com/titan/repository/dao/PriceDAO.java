package com.titan.repository.dao;

import com.titan.repository.mapper.PriceMapper;
import com.titan.repository.db.Datasource;
import com.titan.model.entities.Price;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PriceDAO implements CrudDAO<Price> {
  private final Datasource datasource;
  private final PriceMapper priceMapper;

  public PriceDAO() {
    this.datasource = new Datasource();
    this.priceMapper = new PriceMapper();
  }

  @Override
  public List<Price> getAll(int page, int pageSize) {
    List<Price> prices = new ArrayList<>();
    String query =
        "SELECT price_id, ingredient_id, unit_price, price_date "
            + "FROM ingredient_price LIMIT ? OFFSET ?";

    try (Connection connection = this.datasource.getConnection();
        PreparedStatement st = connection.prepareStatement(query)) {
      st.setInt(1, pageSize);
      st.setInt(2, (page - 1) * pageSize);

      try (ResultSet rs = st.executeQuery()) {
        while (rs.next()) {
          prices.add(priceMapper.apply(rs));
        }
      }

      return prices;
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public Price getById(long id) {
    String query =
        "SELECT price_id, ingredient_id, unit_price, price_date "
            + "FROM ingredient_price WHERE price_id = ?";

    try (Connection connection = this.datasource.getConnection();
        PreparedStatement st = connection.prepareStatement(query)) {
      st.setLong(1, id);

      try (ResultSet rs = st.executeQuery()) {
        if (rs.next()) {
          return priceMapper.apply(rs);
        } else {
          throw new RuntimeException("Price not found");
        }
      }
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public List<Price> saveAll(List<Price> pricesToAdd) {
    List<Price> prices = new ArrayList<>();
    String query =
        "INSERT INTO ingredient_price"
            + "(price_id, ingredient_id, unit_price, price_date) "
            + "VALUES (?,?,?,?) "
            + "ON CONFLICT DO NOTHING "
            + "RETURNING price_id, ingredient_id, unit_price, price_date";

    try (Connection connection = this.datasource.getConnection();
        PreparedStatement st = connection.prepareStatement(query)) {

      pricesToAdd.forEach(
          price -> {
            try {
              st.setLong(1, price.getId());
              st.setLong(2, price.getIngredientId());
              st.setDouble(3, price.getValue());
              st.setTimestamp(4, Timestamp.valueOf(price.getDate()));

              try (ResultSet rs = st.executeQuery()) {
                if (rs.next()) {
                  prices.add(priceMapper.apply(rs));
                }
              }
            } catch (SQLException e) {
              throw new RuntimeException(e);
            }
          });

      return prices;
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  public List<Price> getPricesFor(long ingredientId) {
    List<Price> prices = new ArrayList<>();
    String query =
        "SELECT price_id, ingredient_id, unit_price, price_date "
            + "FROM ingredient_price WHERE ingredient_id = ?";

    try (Connection connection = this.datasource.getConnection();
        PreparedStatement st = connection.prepareStatement(query)) {
      st.setLong(1, ingredientId);

      try (ResultSet rs = st.executeQuery()) {
        while (rs.next()) {
          prices.add(priceMapper.apply(rs));
        }
      }

      return prices;
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }
}
