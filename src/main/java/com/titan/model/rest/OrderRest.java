package com.titan.model.rest;


import com.titan.model.enums.StatusType;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class OrderRest {
    private String reference;
    private double totalAmount;
    private StatusType status;
    private List<DishOrderRest> dishes;
}
