package com.titan.controller.endpoint;

import com.titan.model.entities.SoldDish;
import com.titan.service.DashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class DashboardRestController {
  private final DashboardService dashboardService;

  @GetMapping("/bestSales")
  public ResponseEntity<Object> getBestSales(
      @RequestParam LocalDateTime dateMin,
      @RequestParam LocalDateTime dateMax,
      @RequestParam int size) {
    try {
      List<SoldDish> soldDishes = dashboardService.getBestSales(dateMin, dateMax);
      List<SoldDish> body = new ArrayList<>();
      while (body.size() != size) {
        body.add(soldDishes.removeFirst());
      }
      return ResponseEntity.ok().body(body);
    } catch (Exception e) {
      return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
    }
  }

  @GetMapping("/dishes/{id}/processingTime")
  public ResponseEntity<Object> getProcessingTime(@PathVariable Long id) {
    return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
  }
}
