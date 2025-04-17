package com.titan.service;

import static com.titan.model.enums.StatusType.DONE;
import static com.titan.model.enums.StatusType.IN_PROGRESS;

import com.titan.model.entities.*;
import com.titan.model.enums.DurationType;
import com.titan.model.enums.ProcessingTimeType;
import com.titan.repository.dao.DishOrderDAO;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DashboardService {
  private final DishOrderDAO dishOrderDAO;

  public List<SoldDish> getBestSales(LocalDateTime dateMin, LocalDateTime dateMax) {
    List<DishOrder> dishOrders = dishOrderDAO.getAll(1, 100);
    List<DishOrder> finished =
        dishOrders.stream()
            .filter(
                dishOrder ->
                    dishOrder.getStatusList().stream()
                        .anyMatch(dishOrderStatus -> dishOrderStatus.getStatus().equals(DONE)))
            .toList();
    List<DishOrder> dishOrdersBetweenInterval =
        finished.stream()
            .filter(
                dishOrder ->
                    (dishOrder.getActualStatus().getCreationDate().isAfter(dateMin)
                        && dishOrder.getActualStatus().getCreationDate().isBefore(dateMax)))
            .toList();

    List<SoldDish> soldDishes = new ArrayList<>();
    dishOrdersBetweenInterval.forEach(
        dishOrder -> {
          if (soldDishes.stream()
              .anyMatch(
                  soldDish -> soldDish.getDishName().equals(dishOrder.getDish().getDishName()))) {
            for (int i = 0; i < soldDishes.size(); i++) {
              if (soldDishes.get(i).getDishName().equals(dishOrder.getDish().getDishName())) {
                Double profit = soldDishes.get(i).getTotalProfit();
                soldDishes.get(i).setTotalProfit(profit + dishOrder.getTotalAmount());
                Long quantity = soldDishes.get(i).getTotalQuantities();
                soldDishes.get(i).setTotalQuantities(quantity + dishOrder.getQuantity());
              }
            }
          } else {
            SoldDish soldDish = new SoldDish();

            soldDish.setDishName(dishOrder.getDish().getDishName());
            soldDish.setTotalProfit(dishOrder.getTotalAmount().doubleValue());
            soldDish.setTotalQuantities((long) dishOrder.getQuantity());

            soldDishes.add(soldDish);
          }
        });

    return soldDishes.stream()
        .sorted(Comparator.comparing(SoldDish::getTotalQuantities, Comparator.naturalOrder()))
        .toList();
  }

  public ProcessingTime getProcessingTimeFor(
      Long dishId, ProcessingTimeType processingTimeType, DurationType durationType) {
    List<DishOrder> dishOrders = dishOrderDAO.getByDishId(dishId);
    List<Duration> durations =
        dishOrders.stream()
            .map(
                dishOrder -> {
                  LocalDateTime inProgressDate =
                      dishOrder.getStatusList().stream()
                          .filter(
                              dishOrderStatus -> dishOrderStatus.getStatus().equals(IN_PROGRESS))
                          .toList()
                          .getFirst()
                          .getCreationDate();
                  LocalDateTime doneDate =
                      dishOrder.getStatusList().stream()
                          .filter(dishOrderStatus -> dishOrderStatus.getStatus().equals(DONE))
                          .toList()
                          .getFirst()
                          .getCreationDate();
                  return Duration.between(inProgressDate, doneDate);
                })
            .toList();

    ProcessingTime result = new ProcessingTime();
    Duration duration = null;
    result.setProcessingTimeType(processingTimeType);
    result.setDurationType(durationType);
    switch (processingTimeType) {
      case MINIMUM -> {
        duration = durations.stream().min(Duration::compareTo).get();
      }

      case MAXIMUM -> {
        duration = durations.stream().max(Duration::compareTo).get();
      }

      case AVERAGE -> {
        duration = durations.stream().reduce((Duration::plus)).get().dividedBy(durations.size());
      }
    }

    switch (durationType) {
      case SECOND -> result.setValue(duration.toSeconds());
      case MINUTE -> result.setValue(duration.toMinutes());
      case HOUR -> result.setValue(duration.toHours());
    }

    return result;
  }
}
