package com.titan.entities;

import com.titan.entities.enums.UnitType;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
public class Ingredient {
    private long ingredientId;
    private String ingredientName;
    private UnitType unit;
    private List<Price> ingredientPrices;
    private List<StockMovement> stockMovements;
    private LocalDateTime lastModified;
}
