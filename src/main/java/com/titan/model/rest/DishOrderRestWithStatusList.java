package com.titan.model.rest;

import com.titan.model.entities.Dish;
import com.titan.model.entities.DishOrderStatus;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class DishOrderRestWithStatusList {
    private Long dishOrderId;
    private Long dishId;
    private String dishName;
    private int quantity;
    private Long totalAmount;
    private List<DishOrderStatus> statusList;
    private DishOrderStatus actualStatus;
}
