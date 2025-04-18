package com.titan.controller.endpoint;

import com.titan.controller.mapper.DishOrderRestMapper;
import com.titan.controller.mapper.OrderRestMapper;
import com.titan.model.entities.DishOrder;
import com.titan.model.entities.Order;
import com.titan.model.rest.DishOrderRest;
import com.titan.model.rest.OrderRest;
import com.titan.repository.dao.DishOrderStatusDAO;
import com.titan.repository.dao.OrderDAO;
import com.titan.service.OrderService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class OrderRestController {
  private final OrderService orderService;
  private final OrderRestMapper orderRestMapper;
  private final DishOrderRestMapper dishOrderRestMapper;

  @GetMapping("/orders/{reference}")
  public ResponseEntity<Object> getOrderByReference(@PathVariable String reference) {
    Order order = orderService.getOrderByReference(reference);
    OrderRest orderRest = orderRestMapper.toRest(order);
    return ResponseEntity.ok().body(orderRest);
  }

  @PutMapping("/orders/{reference}/dishes")
  public ResponseEntity<Object> updateDishOrders(
      @PathVariable String reference, @RequestBody List<DishOrderRest> dishOrderRests) {
    List<DishOrder> dishOrders =
        dishOrderRests.stream()
            .map(
                dishOrderRest -> {
                  DishOrder updatedDishOrder = dishOrderRestMapper.toModel(dishOrderRest);
                  updatedDishOrder.setOrderReference(reference);
                  DishOrder originalDishOrder =
                      orderService.getOrderByReference(reference).getDishOrders().stream()
                          .filter(
                              item ->
                                  item.getDish()
                                      .getDishName()
                                      .equals(updatedDishOrder.getDish().getDishName()))
                          .findFirst()
                          .get();
                  updatedDishOrder.setId(originalDishOrder.getId());
                  updatedDishOrder.setStatusList(originalDishOrder.getStatusList());
                  return updatedDishOrder;
                })
            .toList();
    orderService.updateDishOrders(dishOrders);

    Order order = orderService.getOrderByReference(reference);
    OrderRest orderRest = orderRestMapper.toRest(order);
    return ResponseEntity.ok().body(orderRest);
  }

  @PutMapping("/orders/{reference}/dishes/{dishId}")
  public ResponseEntity<Object> updateDishOrderStatus(
      @PathVariable String reference, @PathVariable Long dishId) {
    orderService.updateDishOrderStatus(reference, dishId);

    Order order = orderService.getOrderByReference(reference);
    OrderRest orderRest = orderRestMapper.toRest(order);
    return ResponseEntity.ok().body(orderRest);
  }

    @PostMapping("/orders/{reference}")
    public ResponseEntity<Object> createOrder(@PathVariable String reference) {
        try {
            Order order = orderService.createOrder(reference);
            OrderRest orderRest = orderRestMapper.toRest(order);
            return ResponseEntity.status(201).body(orderRest);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


}
