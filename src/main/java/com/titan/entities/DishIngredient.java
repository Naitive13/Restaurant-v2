package com.titan.entities;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class DishIngredient {
    private Ingredient ingredient;
    private Double quantity;
}
