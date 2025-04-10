package com.titan.controller.mapper;

import com.titan.model.entities.Ingredient;
import com.titan.model.entities.Price;
import com.titan.model.entities.StockMovement;
import com.titan.model.rest.IngredientRest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

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
    Ingredient ingredient = new Ingredient();
    ingredient.setIngredientId(ingredientRest.getId());
    ingredient.setIngredientName(ingredientRest.getName());
    ingredient.setUnit(ingredientRest.getStockMovements().getFirst().getUnit());
    ingredient.setLastModified(LocalDateTime.now());

    ingredient.setIngredientPrices(
        ingredientRest.getPrices().stream()
            .map(
                priceRest -> {
                  Price price = priceRestMapper.toModel(priceRest);
                  price.setIngredientId(ingredient.getIngredientId());
                  return price;
                })
            .toList());
    ingredient.setStockMovements(
        ingredientRest.getStockMovements().stream()
            .map(
                stockMovementRest -> {
                  StockMovement stockMovement = stockMovementRestMapper.toModel(stockMovementRest);
                  stockMovement.setIngredientId(ingredient.getIngredientId());
                  return stockMovement;
                })
            .toList());

    return ingredient;
  }
}
