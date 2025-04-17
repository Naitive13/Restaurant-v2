package com.titan.model.entities;

import static com.titan.model.enums.StatusType.*;
import static java.util.Comparator.naturalOrder;

import com.titan.repository.dao.DishOrderStatusDAO;
import com.titan.repository.dao.OrderDAO;
import com.titan.repository.dao.OrderStatusDAO;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

@Data
@NoArgsConstructor
public class Order {
  private String reference;
  private LocalDateTime creationDate;
  private List<DishOrder> dishOrders;
  private List<OrderStatus> statusList;
  @Autowired
  private OrderStatusDAO orderStatusDAO;

  public OrderStatus getActualStatus() {
    if (this.getStatusList() == null || this.getStatusList().isEmpty()) {
      OrderStatus status = new OrderStatus();
      status.setStatus(CREATED);
      status.setOrderReference(this.getReference());
      status.setCreationDate(LocalDateTime.now());
      status.setId((long) status.hashCode());

      this.setStatusList(List.of(status));
      orderStatusDAO.saveAll(List.of(status));
      return status;
    } else {
      return this.getStatusList().stream()
          .max(Comparator.comparing(OrderStatus::getCreationDate, naturalOrder()))
          .get();
    }
  }

  public void updateStatus() {
    if (this.getStatusList().isEmpty()) {
      OrderStatus status = new OrderStatus();
      status.setStatus(CREATED);
      status.setOrderReference(this.getReference());
      status.setCreationDate(LocalDateTime.now());
      status.setId((long) status.hashCode());

      List<OrderStatus> newStatusList = new ArrayList<>();
      newStatusList.add(status);
      this.setStatusList(newStatusList);
      orderStatusDAO.saveAll(newStatusList);
    } else {
      switch (this.getActualStatus().getStatus()) {
        case CREATED -> {
          if (this.getDishOrders().stream()
              .map(
                  dishOrder -> {
                    if (CREATED.equals(dishOrder.getActualStatus().getStatus())){
                      dishOrder.updateStatus();
                    }

                    return dishOrder.getActualStatus().getStatus();
                  })
              .allMatch(CONFIRMED::equals)) {

            OrderStatus status = new OrderStatus();
            status.setStatus(CONFIRMED);
            status.setOrderReference(this.getReference());
            status.setCreationDate(LocalDateTime.now());
            status.setId((long) status.hashCode());

            List<OrderStatus> newStatusList = new ArrayList<>(this.getStatusList());
            newStatusList.add(status);
            orderStatusDAO.saveAll(newStatusList);

            this.setStatusList(newStatusList);
          }
        }

        case CONFIRMED -> {
          this.getDishOrders().forEach(DishOrder::updateStatus);
          OrderStatus status = new OrderStatus();
          status.setStatus(IN_PROGRESS);
          status.setOrderReference(this.getReference());
          status.setCreationDate(LocalDateTime.now());
          status.setId((long) status.hashCode());

          List<OrderStatus> newStatusList = new ArrayList<>(this.getStatusList());
          newStatusList.add(status);
          this.setStatusList(newStatusList);
          orderStatusDAO.saveAll(newStatusList);
        }

        case IN_PROGRESS -> {
          if (this.getDishOrders().stream()
              .map(dishOrder -> dishOrder.getActualStatus().getStatus())
              .allMatch(DONE::equals)) {

            OrderStatus status = new OrderStatus();
            status.setStatus(DONE);
            status.setOrderReference(this.getReference());
            status.setCreationDate(LocalDateTime.now());
            status.setId((long) status.hashCode());

            List<OrderStatus> newStatusList = new ArrayList<>(this.getStatusList());
            newStatusList.add(status);
            this.setStatusList(newStatusList);
            orderStatusDAO.saveAll(newStatusList);
          }
        }

        case DONE -> {
          if (this.getDishOrders().stream()
              .map(dishOrder -> dishOrder.getActualStatus().getStatus())
              .allMatch(DELIVERED::equals)) {

            OrderStatus status = new OrderStatus();
            status.setStatus(DELIVERED);
            status.setOrderReference(this.getReference());
            status.setCreationDate(LocalDateTime.now());
            status.setId((long) status.hashCode());

            List<OrderStatus> newStatusList = new ArrayList<>(this.getStatusList());
            newStatusList.add(status);
            this.setStatusList(newStatusList);
            orderStatusDAO.saveAll(newStatusList);
          }
        }

        default -> {
          throw new RuntimeException("cannot update status because status is already DELIVERED");
        }
      }
    }
  }

  public Long getTotalAmount() {
    return this.getDishOrders().stream().map(DishOrder::getTotalAmount).reduce(0L, Long::sum);
  }
}
