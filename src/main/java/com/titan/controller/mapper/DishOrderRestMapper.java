package com.titan.controller.mapper;

import com.titan.model.entities.DishOrder;
import com.titan.model.rest.DishOrderRest;
import com.titan.repository.dao.DishDAO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DishOrderRestMapper implements BiMapper<DishOrderRest, DishOrder> {
  private final DishDAO dishDAO;
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
    DishOrder dishOrder = new DishOrder();
    dishOrder.setQuantity(dishOrderRest.getQuantity());
    dishOrder.setDish(dishDAO.getByName(dishOrderRest.getName()));

    return dishOrder;
  }
}
