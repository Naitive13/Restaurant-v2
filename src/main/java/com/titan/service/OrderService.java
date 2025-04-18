package com.titan.service;

import com.titan.model.entities.DishOrder;
import com.titan.model.entities.Order;
import com.titan.repository.dao.DishOrderDAO;
import com.titan.repository.dao.DishOrderStatusDAO;
import com.titan.repository.dao.OrderDAO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderDAO orderDAO;
    private final DishOrderDAO dishOrderDAO;
    private final DishOrderStatusDAO dishOrderStatusDAO;

    public Order getOrderByReference(String reference){
        return orderDAO.getByReference(reference);
    }

    public void updateDishOrders(List<DishOrder> dishOrder) {
        dishOrderDAO.saveAll(dishOrder);
    }

    public void updateDishOrderStatus(String reference, Long dishId) {
        Order order = orderDAO.getByReference(reference);
        order.getDishOrders().forEach(dishOrder -> {
            if (dishOrder.getDish().getDishId() == dishId){
                dishOrder.setDishOrderStatusDAO(dishOrderStatusDAO);
                dishOrder.setOrderDAO(orderDAO);
                dishOrder.updateStatus();
            }
        });
    }
    public Order createOrder(String reference) {
        Order existingOrder;
        try {
            existingOrder = orderDAO.getByReference(reference);
        } catch (RuntimeException e) {
            existingOrder = null; // commande inexistante
        }

        if (existingOrder != null) {
            throw new IllegalArgumentException("Order with reference '" + reference + "' already exists.");
        }

        Order newOrder = new Order();
        newOrder.setReference(reference);
        newOrder.setCreationDate(java.time.LocalDateTime.now());
        newOrder.setDishOrders(List.of()); // vide à la création
        newOrder.setStatusList(List.of()); // vide ou statuts initiaux à gérer si tu veux

        return orderDAO.save(newOrder);
    }


}
