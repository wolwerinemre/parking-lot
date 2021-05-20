package com.swedbank.sample.parkinglot.service;

import java.math.BigDecimal;

public interface CostCalculator {
    BigDecimal calculate(double height, double weight);
}
