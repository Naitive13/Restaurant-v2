package com.titan.controller.endpoint;

import com.titan.controller.mapper.OrderRestMapper;
import com.titan.model.entities.Order;
import com.titan.model.rest.OrderRest;
import com.titan.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class OrderRestController {
    private final OrderService orderService;
    private final OrderRestMapper orderRestMapper;

    @GetMapping("/orders/{reference}")
    public ResponseEntity<Object> getOrderByReference(@PathVariable String reference){
        Order order = orderService.getOrderByReference(reference);
        OrderRest orderRest = orderRestMapper.toRest(order);
        return ResponseEntity.ok().body(orderRest);
    }
}
