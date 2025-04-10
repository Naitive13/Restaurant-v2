package com.titan.model.rest;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class PriceRest {
    private long id;
    private double price;
    private LocalDateTime dateValue;
}
