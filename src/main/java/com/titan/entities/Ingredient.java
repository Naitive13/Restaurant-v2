package com.titan.entities;

import static java.util.Comparator.naturalOrder;

import com.titan.entities.enums.UnitType;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Ingredient {
  private long ingredientId;
  private String ingredientName;
  private UnitType unit;
  private List<Price> ingredientPrices;
  private List<StockMovement> stockMovements;
  private LocalDateTime lastModified;

  public double getActualPrice() {
    return this.getIngredientPrices().stream()
        .max(Comparator.comparing(Price::getDate, naturalOrder()))
        .get()
        .getValue();
  }
}
