package com.titan.model.entities;

import com.titan.model.enums.StockType;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class StockMovement {
    private long id;
    private long ingredientId;
    private double quantity;
    private LocalDateTime lastModified;
    private StockType type;
}
