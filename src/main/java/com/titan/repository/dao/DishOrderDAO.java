package com.titan.repository.dao;

import com.titan.repository.mapper.DishOrderMapper;
import com.titan.repository.db.Datasource;
import com.titan.model.entities.DishOrder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class DishOrderDAO implements CrudDAO<DishOrder> {
  private final Datasource datasource;
  private final DishOrderMapper dishOrderMapper;
  private final DishOrderStatusDAO dishOrderStatusDAO;

  @Override
  public List<DishOrder> getAll(int page, int pageSize) {
    List<DishOrder> dishOrders = new ArrayList<>();
    String query =
        "SELECT dish_order_id, dish_id, order_reference, quantity "
            + "FROM dish_order LIMIT ? OFFSET ?";

    try (Connection connection = this.datasource.getConnection();
        PreparedStatement st = connection.prepareStatement(query)) {
      st.setInt(1, pageSize);
      st.setInt(2, (page - 1) * pageSize);

      try (ResultSet rs = st.executeQuery()) {
        while (rs.next()) {
          dishOrders.add(dishOrderMapper.apply(rs));
        }
      }
      return dishOrders;
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public DishOrder getById(long id) {
    String query =
        "SELECT dish_order_id, dish_id, order_reference, quantity "
            + "FROM dish_order WHERE dish_order_id = ?";

    try (Connection connection = this.datasource.getConnection();
        PreparedStatement st = connection.prepareStatement(query)) {
      st.setLong(1, id);

      try (ResultSet rs = st.executeQuery()) {
        if (rs.next()) {
          return dishOrderMapper.apply(rs);
        } else {
          throw new RuntimeException("DishOrder not found");
        }
      }
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public List<DishOrder> saveAll(List<DishOrder> dishOrdersToAdd) {
    List<DishOrder> dishOrders = new ArrayList<>();
    String query =
        "INSERT INTO dish_order "
            + "(dish_order_id, dish_id, order_reference, quantity) "
            + "VALUES (?,?,?,?)"
            + " ON CONFLICT (dish_id, order_reference) DO UPDATE SET "
            + "quantity = excluded.quantity "
            + "RETURNING dish_order_id, dish_id, order_reference, quantity";

    try (Connection connection = this.datasource.getConnection();
        PreparedStatement st = connection.prepareStatement(query)) {

      dishOrdersToAdd.forEach(
          dishOrder -> {
            try {
              st.setLong(1, dishOrder.getId());
              st.setLong(2, dishOrder.getDish().getDishId());
              st.setString(3, dishOrder.getOrderReference());
              st.setInt(4, dishOrder.getQuantity());

              try (ResultSet rs = st.executeQuery()) {
                dishOrderStatusDAO.saveAll(dishOrder.getStatusList());
                if (rs.next()) {
                  dishOrders.add(dishOrderMapper.apply(rs));
                }
              }

            } catch (Exception e) {
              throw new RuntimeException(e);
            }
          });

      return dishOrders;
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  public List<DishOrder> getDishOrdersFor(String reference) {
    List<DishOrder> dishOrders = new ArrayList<>();
    String query =
        "SELECT dish_order_id, dish_id, order_reference, quantity "
            + "FROM dish_order WHERE order_reference = ?";

    try (Connection connection = this.datasource.getConnection();
        PreparedStatement st = connection.prepareStatement(query)) {
      st.setString(1, reference);

      try (ResultSet rs = st.executeQuery()) {
        while (rs.next()) {
          dishOrders.add(dishOrderMapper.apply(rs));
        }
      }
      return dishOrders;
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }
}
