package com.titan.entities;

import static com.titan.entities.enums.StatusType.*;
import static java.util.Comparator.naturalOrder;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Order {
  private String reference;
  private LocalDateTime creationDate;
  private List<DishOrder> dishOrders;
  private List<OrderStatus> statusList;

  public OrderStatus getActualStatus() {
    if (this.getStatusList() == null || this.getStatusList().isEmpty()) {
      OrderStatus status = new OrderStatus();
      status.setStatus(CREATED);
      status.setOrderReference(this.getReference());
      status.setCreationDate(LocalDateTime.of(2025, 1, 1, 0, 0, 0));

      this.setStatusList(List.of(status));
      return status;
    } else {
      return this.getStatusList().stream()
          .max(Comparator.comparing(OrderStatus::getCreationDate, naturalOrder()))
          .get();
    }
  }

  public void updateStatus() {
    switch (this.getActualStatus().getStatus()) {
      case CREATED -> {
        if (this.getDishOrders().stream()
            .map(dishOrder -> dishOrder.getActualStatus().getStatus())
            .allMatch(CONFIRMED::equals)) {

          OrderStatus status = new OrderStatus();
          status.setStatus(CONFIRMED);
          status.setOrderReference(this.getReference());
          status.setCreationDate(LocalDateTime.of(2025, 1, 2, 0, 0, 0));

          List<OrderStatus> newStatusList = new ArrayList<>(this.getStatusList());
          newStatusList.add(status);

          this.setStatusList(newStatusList);
        }
      }

      case CONFIRMED -> {
        OrderStatus status = new OrderStatus();
        status.setStatus(IN_PROGRESS);
        status.setOrderReference(this.getReference());
        status.setCreationDate(LocalDateTime.of(2025, 1, 3, 0, 0, 0));

        List<OrderStatus> newStatusList = new ArrayList<>(this.getStatusList());
        newStatusList.add(status);

        this.setStatusList(newStatusList);
      }

      case IN_PROGRESS -> {
        if (this.getDishOrders().stream()
            .map(dishOrder -> dishOrder.getActualStatus().getStatus())
            .allMatch(DONE::equals)) {

          OrderStatus status = new OrderStatus();
          status.setStatus(DONE);
          status.setOrderReference(this.getReference());
          status.setCreationDate(LocalDateTime.of(2025, 1, 4, 0, 0, 0));

          List<OrderStatus> newStatusList = new ArrayList<>(this.getStatusList());
          newStatusList.add(status);

          this.setStatusList(newStatusList);
        }
      }

      case DONE -> {
        if (this.getDishOrders().stream()
            .map(dishOrder -> dishOrder.getActualStatus().getStatus())
            .allMatch(DELIVERED::equals)) {

          OrderStatus status = new OrderStatus();
          status.setStatus(DELIVERED);
          status.setOrderReference(this.getReference());
          status.setCreationDate(LocalDateTime.of(2025, 1, 5, 0, 0, 0));

          List<OrderStatus> newStatusList = new ArrayList<>(this.getStatusList());
          newStatusList.add(status);

          this.setStatusList(newStatusList);
        }
      }

      default -> {
        throw new RuntimeException("cannot update status because status is already DELIVERED");
      }
    }
  }

  public Long getTotalAmount() {
    return this.getDishOrders().stream().map(DishOrder::getTotalAmount).reduce(0L, Long::sum);
  }
}
