package com.titan.model.rest;

import com.titan.model.enums.StockType;
import com.titan.model.enums.UnitType;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class StockMovementRest {
    private long id;
    private double quantity;
    private UnitType unit;
    private StockType type;
    private LocalDateTime creationDatetime;
}
