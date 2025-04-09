package com.titan.entities;

import static com.titan.entities.enums.StatusType.*;
import static com.titan.entities.enums.StatusType.DONE;
import static java.util.Comparator.naturalOrder;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class DishOrder {
  private Long id;
  private String orderReference;
  private Dish dish;
  private int quantity;
  private List<DishOrderStatus> statusList;

  public DishOrderStatus getActualStatus() {
    if (this.getStatusList() == null || this.getStatusList().isEmpty()) {
      DishOrderStatus status = new DishOrderStatus();
      status.setStatus(CREATED);
      status.setDishOrderId(this.getId());
      status.setCreationDate(LocalDateTime.now());
      return status;
    } else {
      return this.getStatusList().stream()
          .max(Comparator.comparing(DishOrderStatus::getCreationDate, naturalOrder()))
          .get();
    }
  }

  public void updateStatus() {
    switch (this.getActualStatus().getStatus()) {
      case CREATED -> {
        if (dish.getAvailableQuantity() >= quantity) {
          DishOrderStatus status = new DishOrderStatus();
          status.setStatus(CONFIRMED);
          status.setDishOrderId(this.getId());
          status.setCreationDate(LocalDateTime.of(2025, 1, 2, 0, 0, 0));

          List<DishOrderStatus> newStatusList = new ArrayList<>(this.getStatusList());
          newStatusList.add(status);

          this.setStatusList(newStatusList);
        } else {
          throw new RuntimeException("Not enough ingredient for " + dish.getDishName());
        }
      }

      case CONFIRMED -> {
        DishOrderStatus status = new DishOrderStatus();
        status.setStatus(IN_PROGRESS);
        status.setDishOrderId(this.getId());
        status.setCreationDate(LocalDateTime.of(2025, 1, 3, 0, 0, 0));

        List<DishOrderStatus> newStatusList = new ArrayList<>(this.getStatusList());
        newStatusList.add(status);

        this.setStatusList(newStatusList);
      }

      case IN_PROGRESS -> {
        DishOrderStatus status = new DishOrderStatus();
        status.setStatus(DONE);
        status.setDishOrderId(this.getId());
        status.setCreationDate(LocalDateTime.of(2025, 1, 4, 0, 0, 0));

        List<DishOrderStatus> newStatusList = new ArrayList<>(this.getStatusList());
        newStatusList.add(status);

        this.setStatusList(newStatusList);
      }

      case DONE -> {
        DishOrderStatus status = new DishOrderStatus();
        status.setStatus(DELIVERED);
        status.setDishOrderId(this.getId());
        status.setCreationDate(LocalDateTime.of(2025, 1, 5, 0, 0, 0));

        List<DishOrderStatus> newStatusList = new ArrayList<>(this.getStatusList());
        newStatusList.add(status);

        this.setStatusList(newStatusList);
      }

      default -> {
        throw new RuntimeException("cannot update status because status is already DELIVERED");
      }
    }
  }

  public Long getTotalAmount(){
    return this.getQuantity() * this.getDish().getDishPrice();
  }
}
