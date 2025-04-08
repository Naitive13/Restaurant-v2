package com.titan.dao;

import com.titan.dao.mapper.StockMovementMapper;
import com.titan.db.Datasource;
import com.titan.entities.StockMovement;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StockDAO implements CrudDAO<StockMovement> {
  private final Datasource datasource;
  private final StockMovementMapper stockMovementMapper;

  public StockDAO() {
    this.datasource = new Datasource();
    this.stockMovementMapper = new StockMovementMapper();
  }

  @Override
  public List<StockMovement> getAll(int page, int pageSize) {
    List<StockMovement> stocks = new ArrayList<>();
    String query =
        "SELECT stock_id, ingredient_id, quantity, movement, last_modified "
            + "FROM stock LIMIT ? OFFSET ?";

    try (Connection connection = this.datasource.getConnection();
        PreparedStatement st = connection.prepareStatement(query)) {
      st.setInt(1, page);
      st.setInt(2, (page - 1) * pageSize);

      try (ResultSet rs = st.executeQuery()) {
        while (rs.next()) {
          stocks.add(stockMovementMapper.apply(rs));
        }
      }
      return stocks;
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public StockMovement getById(long id) {
    String query =
        "SELECT stock_id, ingredient_id, quantity, movement, last_modified "
            + "FROM stock WHERE stock_id = ?";

    try (Connection connection = this.datasource.getConnection();
        PreparedStatement st = connection.prepareStatement(query)) {
      st.setLong(1, id);

      try (ResultSet rs = st.executeQuery()) {
        if (rs.next()) {
          return stockMovementMapper.apply(rs);
        } else {
          throw new RuntimeException("Stock not found");
        }
      }
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public List<StockMovement> saveAll(List<StockMovement> stockMovementsToAdd) {
    List<StockMovement> stocks = new ArrayList<>();
    if (!stockMovementsToAdd.isEmpty()) {
      String query =
          "INSERT INTO stock "
              + "(stock_id, ingredient_id, quantity, movement, last_modified)"
              + "VALUES (?,?,?,?::movement_type,?) "
              + "ON CONFLICT DO NOTHING "
              + "RETURNING stock_id, ingredient_id, quantity, movement, last_modified";

      try (Connection connection = this.datasource.getConnection();
          PreparedStatement st = connection.prepareStatement(query)) {

        stockMovementsToAdd.forEach(
            stockMovement -> {
              try {
                st.setLong(1, stockMovement.getId());
                st.setLong(2, stockMovement.getIngredientId());
                st.setDouble(3, stockMovement.getQuantity());
                st.setString(4, stockMovement.getType().toString());
                st.setTimestamp(5, Timestamp.valueOf(stockMovement.getLastModified()));

                try (ResultSet rs = st.executeQuery()) {
                  if (rs.next()) {
                    stocks.add(stockMovementMapper.apply(rs));
                  }
                }
              } catch (SQLException e) {
                throw new RuntimeException(e);
              }
            });

      } catch (Exception e) {
        throw new RuntimeException(e);
      }
    }
    return stocks;
  }

  public List<StockMovement> getStockMovementsFor(long ingredientId) {
    List<StockMovement> stocks = new ArrayList<>();
    String query =
        "SELECT stock_id, ingredient_id, quantity, movement, last_modified "
            + "FROM stock WHERE ingredient_id = ?";

    try (Connection connection = this.datasource.getConnection();
        PreparedStatement st = connection.prepareStatement(query)) {
      st.setLong(1, ingredientId);

      try (ResultSet rs = st.executeQuery()) {
        while (rs.next()) {
          stocks.add(stockMovementMapper.apply(rs));
        }
      }
      return stocks;
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }
}
