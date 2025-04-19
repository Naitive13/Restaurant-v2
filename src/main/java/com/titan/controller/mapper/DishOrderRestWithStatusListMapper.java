package com.titan.controller.mapper;

import com.titan.model.entities.DishOrder;
import com.titan.model.rest.DishOrderRestWithStatusList;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class DishOrderRestWithStatusListMapper
    implements Function<DishOrder, DishOrderRestWithStatusList> {
  @Override
  public DishOrderRestWithStatusList apply(DishOrder dishOrder) {
    DishOrderRestWithStatusList item = new DishOrderRestWithStatusList();
    
    item.setSalesPoint("Analamahintsy");
    item.setDishOrderId(dishOrder.getId());
    item.setDishId(dishOrder.getDish().getDishId());
    item.setStatusList(dishOrder.getStatusList());
    item.setDishName(dishOrder.getDish().getDishName());
    item.setActualStatus(dishOrder.getActualStatus().getStatus());
    item.setQuantity(dishOrder.getQuantity());
    item.setTotalAmount(dishOrder.getTotalAmount());

    return item;
  }
}
