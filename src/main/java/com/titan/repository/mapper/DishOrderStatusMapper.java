package com.titan.repository.mapper;

import com.titan.model.entities.DishOrderStatus;
import com.titan.model.enums.StatusType;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.util.function.Function;

@Component
public class DishOrderStatusMapper implements Function<ResultSet, DishOrderStatus> {
  @Override
  @SneakyThrows
  public DishOrderStatus apply(ResultSet rs) {
    DishOrderStatus dishOrderStatus = new DishOrderStatus();

    dishOrderStatus.setId(rs.getLong("status_id"));
    dishOrderStatus.setDishOrderId(rs.getLong("dish_order_id"));
    dishOrderStatus.setStatus(StatusType.valueOf(rs.getString("dish_order_status")));
    dishOrderStatus.setCreationDate(rs.getTimestamp("creation_date").toLocalDateTime());

    return dishOrderStatus;
  }
}
