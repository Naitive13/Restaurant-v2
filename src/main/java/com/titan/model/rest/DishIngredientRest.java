package com.titan.model.rest;

import com.titan.model.enums.UnitType;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class DishIngredientRest {
    private Long id;
    private String name;
    private Double requiredQuantity;
    private UnitType unit;
}
