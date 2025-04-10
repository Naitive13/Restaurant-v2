package com.titan.controller.mapper;

import com.titan.model.entities.Ingredient;
import com.titan.model.rest.IngredientRest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class IngredientRestMapper implements BiMapper<IngredientRest, Ingredient> {
  private final StockMovementRestMapper stockMovementRestMapper;
  private final PriceRestMapper priceRestMapper;

  @Override
  public IngredientRest toRest(Ingredient ingredient) {
    IngredientRest ingredientRest = new IngredientRest();

    ingredientRest.setId(ingredient.getIngredientId());
    ingredientRest.setName(ingredient.getIngredientName());
    ingredientRest.setActualPrice(ingredient.getActualPrice());
    ingredientRest.setAvailableQuantity(ingredient.getAvailableQuantity());
    ingredientRest.setStockMovements(
        ingredient.getStockMovements().stream().map(stockMovementRestMapper::toRest).toList());
    ingredientRest.setPrices(
        ingredient.getIngredientPrices().stream().map(priceRestMapper::toRest).toList());

    ingredientRest
        .getStockMovements()
        .forEach(stockMovementRest -> stockMovementRest.setUnit(ingredient.getUnit()));

    return ingredientRest;
  }

  @Override
  public Ingredient toModel(IngredientRest ingredientRest) {
    return null;
  }
}
