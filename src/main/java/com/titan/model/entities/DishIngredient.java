package com.titan.model.entities;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class DishIngredient {
  private Long dishId;
  private Ingredient ingredient;
  private Double quantity;

  public Double getIngredientCost() {
    return this.ingredient.getActualPrice() * this.quantity;
  }
}
