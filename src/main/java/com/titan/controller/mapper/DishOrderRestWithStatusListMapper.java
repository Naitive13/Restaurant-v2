package com.titan.controller.mapper;

import com.titan.model.entities.DishOrder;
import com.titan.model.rest.DishOrderRestWithStatusList;

import java.util.function.Function;

public class DishOrderRestWithStatusListMapper
    implements Function<DishOrder, DishOrderRestWithStatusList> {
  @Override
  public DishOrderRestWithStatusList apply(DishOrder dishOrder) {
    DishOrderRestWithStatusList item = new DishOrderRestWithStatusList();
    item.setDishId(dishOrder.getDish().getDishId());
    item.setStatusList(dishOrder.getStatusList());
    item.setDishName(dishOrder.getDish().getDishName());
    item.setActualStatus(dishOrder.getActualStatus());
    item.setQuantity(dishOrder.getQuantity());
    item.setTotalAmount(dishOrder.getTotalAmount());

    return item;
  }
}
