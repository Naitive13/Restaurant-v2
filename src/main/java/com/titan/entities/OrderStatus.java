package com.titan.entities;

import com.titan.entities.enums.StatusType;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class OrderStatus {
    private String orderReference;
    private StatusType status;
    private LocalDateTime creationDate;
}
