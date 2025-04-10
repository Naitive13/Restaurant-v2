package com.titan.model.entities;

import com.titan.model.enums.StatusType;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class DishOrderStatus {
  private Long id;
  private Long dishOrderId;
  private StatusType status;
  private LocalDateTime creationDate;
}
