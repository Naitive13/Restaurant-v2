package com.titan.service;

import com.titan.model.entities.Order;
import com.titan.repository.dao.OrderDAO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderDAO orderDAO;

    public Order getOrderByReference(String reference){
        return orderDAO.getByReference(reference);
    }
}
