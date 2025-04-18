package com.titan.model.entities;

import static com.titan.model.enums.StockType.IN;
import static com.titan.model.enums.StockType.OUT;
import static java.util.Comparator.naturalOrder;

import com.titan.model.enums.UnitType;
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
    Price price = new Price();
    price.setValue(0);
    return this.getIngredientPrices().stream()
        .max(Comparator.comparing(Price::getDate, naturalOrder())).orElse(price)
        .getValue();
  }

  public double getActualPriceAt(LocalDateTime dateTime) {
    return this.getIngredientPrices().stream()
        .filter(price -> price.getDate().equals(dateTime))
        .findFirst()
        .orElseThrow()
        .getValue();
  }

  public Double getAvailableQuantity() {
    return this.getAvailableQuantityAt(LocalDateTime.now());
  }

  public Double getAvailableQuantityAt(LocalDateTime datetime) {
    List<StockMovement> stockMovementsBeforeToday =
        stockMovements.stream()
            .filter(
                stockMovement ->
                    stockMovement.getLastModified().isBefore(datetime)
                        || stockMovement.getLastModified().equals(datetime))
            .toList();
    double quantity = 0;
    for (StockMovement stockMovement : stockMovementsBeforeToday) {
      if (IN.equals(stockMovement.getType())) {
        quantity += stockMovement.getQuantity();
      } else if (OUT.equals(stockMovement.getType())) {
        quantity -= stockMovement.getQuantity();
      }
    }
    return quantity;
  }
}
