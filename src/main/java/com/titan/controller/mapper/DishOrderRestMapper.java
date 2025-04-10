package com.titan.controller.mapper;

import com.titan.model.entities.DishOrder;
import com.titan.model.rest.DishOrderRest;
import org.springframework.stereotype.Component;

@Component
public class DishOrderRestMapper implements BiMapper<DishOrderRest, DishOrder> {
  @Override
  public DishOrderRest toRest(DishOrder dishOrder) {
    DishOrderRest dishOrderRest = new DishOrderRest();

    dishOrderRest.setName(dishOrder.getDish().getDishName());
    dishOrderRest.setQuantity(dishOrder.getQuantity());
    dishOrderRest.setStatus(dishOrder.getActualStatus().getStatus());
    dishOrderRest.setPrice((double) dishOrder.getDish().getDishPrice());

    return dishOrderRest;
  }

  @Override
  public DishOrder toModel(DishOrderRest dishOrderRest) {
    return null;
  }
}
