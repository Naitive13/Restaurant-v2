package com.titan.entities;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class Dish {
    private long dishId;
    private String dishName;
    private long dishPrice;
    private List<Ingredient> ingredientList;
}
