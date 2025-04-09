package com.titan.dao.mapper;

import com.titan.entities.OrderStatus;
import com.titan.entities.enums.StatusType;
import lombok.SneakyThrows;

import java.sql.ResultSet;
import java.util.function.Function;

public class OrderStatusMapper implements Function<ResultSet, OrderStatus> {
  @Override
  @SneakyThrows
  public OrderStatus apply(ResultSet rs) {
    OrderStatus orderStatus = new OrderStatus();

    orderStatus.setId(rs.getLong("status_id"));
    orderStatus.setOrderReference(rs.getString("order_reference"));
    orderStatus.setStatus(StatusType.valueOf(rs.getString("order_status")));
    orderStatus.setCreationDate(rs.getTimestamp("creation_date").toLocalDateTime());

    return orderStatus;
  }
}
