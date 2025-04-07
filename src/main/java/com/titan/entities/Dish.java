package com.titan.entities;

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

  public Double getGrossMargin (){
    return this.getDishPrice() - this.getIngredientsCost();
  }
}
