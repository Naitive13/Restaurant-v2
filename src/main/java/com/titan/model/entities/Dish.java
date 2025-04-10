package com.titan.model.entities;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class Dish {
  private long dishId;
  private String dishName;
  private long dishPrice;
  private List<DishIngredient> ingredientList;

  public Double getIngredientsCost() {
    return this.getIngredientList().stream()
        .map(DishIngredient::getIngredientCost)
        .reduce(0d, Double::sum);
  }

  public Double getGrossMargin() {
    return this.getDishPrice() - this.getIngredientsCost();
  }

  public long getAvailableQuantity() {
    return this.getIngredientList().stream()
        .map(
            dishIngredient -> {
              return Math.round(
                  dishIngredient.getIngredient().getAvailableQuantity()
                      / dishIngredient.getQuantity());
            })
        .min(Long::compare)
        .orElseThrow();
  }
}
