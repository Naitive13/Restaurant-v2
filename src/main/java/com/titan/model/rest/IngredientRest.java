package com.titan.model.rest;


import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class IngredientRest {
    private long id;
    private String name;
    private List<PriceRest> prices;
    private List<StockMovementRest> stockMovements;
    private double availableQuantity;
    private double actualPrice;
}
