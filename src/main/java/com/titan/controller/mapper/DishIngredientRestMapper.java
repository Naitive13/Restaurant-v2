package com.titan.controller.mapper;

import com.titan.model.entities.DishIngredient;
import com.titan.model.rest.DishIngredientRest;
import com.titan.repository.dao.IngredientDAO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DishIngredientRestMapper implements BiMapper<DishIngredientRest, DishIngredient> {
  private final IngredientDAO ingredientDAO;
  @Override
  public DishIngredientRest toRest(DishIngredient dishIngredient) {
    DishIngredientRest dishIngredientRest = new DishIngredientRest();
    dishIngredientRest.setId(dishIngredient.getIngredient().getIngredientId());
    dishIngredientRest.setName(dishIngredient.getIngredient().getIngredientName());
    dishIngredientRest.setRequiredQuantity(dishIngredient.getQuantity());
    dishIngredientRest.setUnit(dishIngredient.getIngredient().getUnit());

    return dishIngredientRest;
  }

  @Override
  public DishIngredient toModel(DishIngredientRest dishIngredientRest) {
    DishIngredient dishIngredient = new DishIngredient();
    dishIngredient.setIngredient(ingredientDAO.getByName(dishIngredientRest.getName()));
    dishIngredient.setQuantity(dishIngredientRest.getRequiredQuantity());

    return dishIngredient;
  }
}
