package com.titan.controller.mapper;

import com.titan.model.entities.Dish;
import com.titan.model.rest.DishRest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DishRestMapper implements BiMapper<DishRest, Dish> {
  private final DishIngredientRestMapper dishIngredientRestMapper;

  @Override
  public DishRest toRest(Dish dish) {
    DishRest dishRest = new DishRest();
    dishRest.setId(dish.getDishId());
    dishRest.setName(dish.getDishName());
    dishRest.setAvailableQuantity(dish.getAvailableQuantity());
    dishRest.setActualPrice((double) dish.getDishPrice());
    dishRest.setIngredients(
        dish.getIngredientList().stream().map(dishIngredientRestMapper::toRest).toList());

    return dishRest;
  }

  @Override
  public Dish toModel(DishRest dishRest) {
    return null;
  }
}
