package com.titan.repository.mapper;

import com.titan.repository.dao.IngredientDAO;
import com.titan.model.entities.DishIngredient;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.util.function.Function;

@Component
@RequiredArgsConstructor
public class DishIngredientMapper implements Function<ResultSet, DishIngredient> {
  private final IngredientDAO ingredientDAO;

  @Override
  @SneakyThrows
  public DishIngredient apply(ResultSet rs) {
    DishIngredient dishIngredient = new DishIngredient();

    dishIngredient.setQuantity(rs.getDouble("quantity"));
    dishIngredient.setDishId(rs.getLong("dish_id"));
    dishIngredient.setIngredient(ingredientDAO.getById(rs.getLong("ingredient_id")));

    return dishIngredient;
  }
}
