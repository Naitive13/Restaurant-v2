package com.titan.model.entities;

import com.titan.model.enums.DurationType;
import com.titan.model.enums.ProcessingTimeType;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Duration;

@Data
@NoArgsConstructor
public class ProcessingTime {
    private ProcessingTimeType processingTimeType;
    private DurationType durationType;
    private Long value;
}
