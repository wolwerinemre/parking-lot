package com.swedbank.sample.parkinglot;

import com.swedbank.sample.parkinglot.config.properties.ParkingLotProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(value = ParkingLotProperties.class)
public class ParkinglotApplication {

    public static void main(String[] args) {
        SpringApplication.run(ParkinglotApplication.class, args);
    }

}
