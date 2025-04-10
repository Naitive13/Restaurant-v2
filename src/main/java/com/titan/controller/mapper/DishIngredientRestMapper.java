package com.titan.controller.mapper;

import com.titan.model.entities.DishIngredient;
import com.titan.model.rest.DishIngredientRest;
import org.springframework.stereotype.Component;

@Component
public class DishIngredientRestMapper implements BiMapper<DishIngredientRest, DishIngredient> {
  @Override
  public DishIngredientRest toRest(DishIngredient dishIngredient) {
    DishIngredientRest dishIngredientRest = new DishIngredientRest();
    dishIngredientRest.setId(dishIngredient.getDishId());
    dishIngredientRest.setName(dishIngredient.getIngredient().getIngredientName());
    dishIngredientRest.setRequiredQuantity(dishIngredient.getQuantity());
    dishIngredientRest.setUnitType(dishIngredient.getIngredient().getUnit());

    return dishIngredientRest;
  }

  @Override
  public DishIngredient toModel(DishIngredientRest dishIngredientRest) {
    return null;
  }
}
