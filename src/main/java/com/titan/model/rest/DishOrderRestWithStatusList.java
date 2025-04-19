package com.titan.model.rest;

import com.titan.model.entities.Dish;
import com.titan.model.entities.DishOrderStatus;
import com.titan.model.enums.StatusType;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class DishOrderRestWithStatusList {
    private Long dishOrderId;
    private String salesPoint;
    private Long dishId;
    private String dishName;
    private int quantity;
    private Long totalAmount;
    private List<DishOrderStatus> statusList;
    private StatusType actualStatus;
}
