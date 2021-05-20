package com.swedbank.sample.parkinglot.dto;

import com.swedbank.sample.parkinglot.domain.ParkingSlot;
import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

@Data
@Accessors(chain = true)
public class ParkingDto {
    private ParkingSlot parkingSlot;
    private BigDecimal costOfMinute;
}