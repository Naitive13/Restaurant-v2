package com.titan.dao.mapper;

import com.titan.dao.IngredientDAO;
import com.titan.entities.DishIngredient;
import lombok.SneakyThrows;

import java.sql.ResultSet;
import java.util.function.Function;

public class DishIngredientMapper implements Function<ResultSet, DishIngredient> {
  @Override
  @SneakyThrows
  public DishIngredient apply(ResultSet rs) {
    IngredientDAO ingredientDAO = new IngredientDAO();
    DishIngredient dishIngredient = new DishIngredient();

    dishIngredient.setQuantity(rs.getDouble("quantity"));
    dishIngredient.setDishId(rs.getLong("dish_id"));
    dishIngredient.setIngredient(ingredientDAO.getById(rs.getLong("ingredient_id")));

    return dishIngredient;
  }
}
