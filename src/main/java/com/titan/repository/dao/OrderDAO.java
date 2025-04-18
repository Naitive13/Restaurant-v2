package com.titan.repository.dao;

import com.titan.repository.mapper.OrderMapper;
import com.titan.repository.db.Datasource;
import com.titan.model.entities.Order;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class OrderDAO implements CrudDAO<Order> {
  private final Datasource datasource;
  private final OrderMapper orderMapper;
  private final DishOrderDAO dishOrderDAO;
  private final OrderStatusDAO orderStatusDAO;

  @Override
  public List<Order> getAll(int page, int pageSize) {
    List<Order> orders = new ArrayList<>();
    String query = "SELECT order_reference, creation_date FROM \"order\" LIMIT ? OFFSET ?";

    try (Connection connection = this.datasource.getConnection();
        PreparedStatement st = connection.prepareStatement(query)) {
      st.setInt(1, pageSize);
      st.setInt(2, (page - 1) * pageSize);

      try (ResultSet rs = st.executeQuery()) {
        while (rs.next()) {
          orders.add(orderMapper.apply(rs));
        }
      }
      return orders;
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public Order getById(long id) {
    throw new UnsupportedOperationException("You should use getByReference instead of getByReference because orders only have a reference");
  }

  public Order getByReference(String reference) {
    String query = "SELECT order_reference, creation_date FROM \"order\" WHERE order_reference = ?";

    try (Connection connection = this.datasource.getConnection();
        PreparedStatement st = connection.prepareStatement(query)) {
      st.setString(1, reference);

      try (ResultSet rs = st.executeQuery()) {
        if (rs.next()) {
          return orderMapper.apply(rs);
        } else {
          throw new RuntimeException("order was not found");
        }
      }
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public List<Order> saveAll(List<Order> ordersToAdd) {
    List<Order> orders = new ArrayList<>();
    String query =
        "INSERT INTO \"order\" "
            + "(order_reference, creation_date) "
            + "VALUES (?,?) ON CONFLICT DO NOTHING "
            + "RETURNING order_reference, creation_date";

    try (Connection connection = this.datasource.getConnection();
        PreparedStatement st = connection.prepareStatement(query)) {

      ordersToAdd.forEach(
          order -> {
            try {
              st.setString(1, order.getReference());
              st.setTimestamp(2, Timestamp.valueOf(order.getCreationDate()));

              try(ResultSet rs = st.executeQuery()){
                dishOrderDAO.saveAll(order.getDishOrders());
                orderStatusDAO.saveAll(order.getStatusList());

                if (rs.next()) {
                  orders.add(orderMapper.apply(rs));
                }
              }
            } catch (Exception e) {
              throw new RuntimeException(e);
            }
          });

      return orders;
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }
  public Order save(Order order) {
    return saveAll(List.of(order)).stream().findFirst().orElseThrow(() ->
            new RuntimeException("Failed to insert the order with reference: " + order.getReference()));
  }

}
