package com.swedbank.sample.parkinglot.config.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.math.BigDecimal;

@Getter
@Setter
@ConfigurationProperties("parking.lot")
public class ParkingLotProperties {
    private BigDecimal costPerMinute;
    private Integer floorLevel;
    private Double height;
    private Double weight;
    private Integer slot;
}
