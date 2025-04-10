package com.titan.repository.dao;

import com.titan.repository.mapper.OrderStatusMapper;
import com.titan.repository.db.Datasource;
import com.titan.model.entities.OrderStatus;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrderStatusDAO implements CrudDAO<OrderStatus> {
  private final Datasource datasource;
  private final OrderStatusMapper orderStatusMapper;

  public OrderStatusDAO() {
    this.orderStatusMapper = new OrderStatusMapper();
    this.datasource = new Datasource();
  }

  @Override
  public List<OrderStatus> getAll(int page, int pageSize) {
    List<OrderStatus> orderStatusList = new ArrayList<>();
    String query =
        "SELECT status_id, order_reference,order_status, creation_date "
            + "FROM order_status LIMIT ? OFFSET ?";

    try (Connection connection = this.datasource.getConnection();
        PreparedStatement st = connection.prepareStatement(query)) {
      st.setInt(1, pageSize);
      st.setInt(2, (page - 1) * pageSize);

      try (ResultSet rs = st.executeQuery()) {
        while (rs.next()) {
          orderStatusList.add(orderStatusMapper.apply(rs));
        }
      }
      return orderStatusList;
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public OrderStatus getById(long id) {
    String query =
        "SELECT status_id, order_reference,order_status, creation_date "
            + "FROM order_status WHERE status_id = ?";

    try (Connection connection = this.datasource.getConnection();
        PreparedStatement st = connection.prepareStatement(query)) {
      st.setLong(1, id);

      try (ResultSet rs = st.executeQuery()) {
        if (rs.next()) {
          return orderStatusMapper.apply(rs);
        } else {
          throw new RuntimeException("Order Status not found");
        }
      }

    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public List<OrderStatus> saveAll(List<OrderStatus> orderStatusesToAdd) {
    List<OrderStatus> orderStatusList = new ArrayList<>();

    if (orderStatusesToAdd != null && !orderStatusesToAdd.isEmpty()) {
      String query =
          "INSERT INTO order_status "
              + "(status_id, order_reference, order_status, creation_date) "
              + "VALUES (?,?,?::statusType,?) "
              + "ON CONFLICT (order_reference, order_status) DO NOTHING "
              + "RETURNING status_id, order_reference, order_status, creation_date";

      try (Connection connection = this.datasource.getConnection();
          PreparedStatement st = connection.prepareStatement(query)) {

        orderStatusesToAdd.forEach(
            dishOrderStatus -> {
              try {
                st.setLong(1, dishOrderStatus.getId());
                st.setString(2, dishOrderStatus.getOrderReference());
                st.setString(3, dishOrderStatus.getStatus().toString());
                st.setTimestamp(4, Timestamp.valueOf(dishOrderStatus.getCreationDate()));

                try (ResultSet rs = st.executeQuery()) {
                  if (rs.next()) {
                    orderStatusList.add(orderStatusMapper.apply(rs));
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

    return orderStatusList;
  }

  public List<OrderStatus> getStatusFor(String reference) {
    List<OrderStatus> orderStatusList = new ArrayList<>();
    String query =
        "SELECT status_id, order_reference,order_status, creation_date "
            + "FROM order_status WHERE order_reference = ?";

    try (Connection connection = this.datasource.getConnection();
        PreparedStatement st = connection.prepareStatement(query)) {
      st.setString(1, reference);

      try (ResultSet rs = st.executeQuery()) {
        while (rs.next()) {
          orderStatusList.add(orderStatusMapper.apply(rs));
        }
      }
      return orderStatusList;
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }
}
