package com.titan.controller.mapper;

import com.titan.model.entities.Order;
import com.titan.model.rest.OrderRest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OrderRestMapper implements BiMapper<OrderRest, Order> {
  private final DishOrderRestMapper dishOrderRestMapper;

  @Override
  public OrderRest toRest(Order order) {
    OrderRest orderRest = new OrderRest();

    orderRest.setReference(order.getReference());
    orderRest.setStatus(order.getActualStatus().getStatus());
    orderRest.setDishOrder(
        order.getDishOrders().stream().map(dishOrderRestMapper::toRest).toList());

    return orderRest;
  }

  @Override
  public Order toModel(OrderRest orderRest) {
    return null;
  }
}
