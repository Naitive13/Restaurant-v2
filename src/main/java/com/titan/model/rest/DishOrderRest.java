package com.titan.model.rest;

import com.titan.model.enums.StatusType;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class DishOrderRest {
    private String name;
    private int quantity;
    private StatusType status;
    private Double price;
}
