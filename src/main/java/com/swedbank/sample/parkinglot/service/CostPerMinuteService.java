package com.swedbank.sample.parkinglot.service;

import com.swedbank.sample.parkinglot.config.properties.ParkingLotProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Service
public class CostPerMinuteService implements CostCalculator{

    private final ParkingLotProperties parkingLotProperties;

    @Autowired
    public CostPerMinuteService(ParkingLotProperties parkingLotProperties) {
        this.parkingLotProperties = parkingLotProperties;
    }

    @Override
    public BigDecimal calculate(double height, double weight) {
        return BigDecimal.valueOf(height)
                .multiply(BigDecimal.valueOf(weight))
                .divide(BigDecimal.valueOf(6000L), 2, RoundingMode.HALF_UP)
                .multiply(parkingLotProperties.getCostPerMinute())
                .setScale(2, RoundingMode.HALF_UP);
    }
}
