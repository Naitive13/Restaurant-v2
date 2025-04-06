package com.titan.entities;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class Price {
    private long ingredientId;
    private double value;
    private LocalDateTime date;
}
