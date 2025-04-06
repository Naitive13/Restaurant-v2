package com.titan.entities;

import com.titan.entities.enums.StockType;
import com.titan.entities.enums.UnitType;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class StockMovement {
    private long ingredientId;
    private double quantity;
    private UnitType unit;
    private LocalDateTime lastModified;
    private StockType type;
}
