package com.titan.model.rest;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class DishRest {
    private Long id;
    private String name;
    private Long availableQuantity;
    private Double actualPrice;
    private List<DishIngredientRest> ingredients;
}
