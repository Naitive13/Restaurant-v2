package com.titan.service;

import com.titan.model.entities.DishOrder;
import com.titan.model.entities.Order;
import com.titan.repository.dao.DishOrderDAO;
import com.titan.repository.dao.OrderDAO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderDAO orderDAO;
    private final DishOrderDAO dishOrderDAO;

    public Order getOrderByReference(String reference){
        return orderDAO.getByReference(reference);
    }

    public void updateDishOrders(List<DishOrder> dishOrder) {
        dishOrderDAO.saveAll(dishOrder);
    }
}
