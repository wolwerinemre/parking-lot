package com.swedbank.sample.parkinglot.service;

import com.swedbank.sample.parkinglot.config.properties.ParkingLotProperties;
import com.swedbank.sample.parkinglot.dto.ParkingDto;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.math.BigDecimal;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
@EnableConfigurationProperties
@TestPropertySource("classpath:application.properties")
public class ParkingServiceTests {

    @Autowired
    private ParkingLotProperties parkingLotProperties;
    @Mock
    private CostPerMinuteService costPerMinuteService;
    private ParkingService parkingService;

    @BeforeEach
    void init() {
        parkingService = new ParkingService(costPerMinuteService, parkingLotProperties);
    }

    @Test
    void test_parkAndCost(){
        String plate = "Test123";
        Double weight = 100d;
        Double height = 100d;
        BigDecimal cost = BigDecimal.valueOf(0.17d);
        ParkingDto parkingDto = parkingService.parkAndCost(plate, height, weight);
        Assertions.assertThat(parkingDto.getParkingSlot().getVehicle().getPlate()).isEqualTo(plate);
        Assertions.assertThat(parkingDto.getParkingSlot().getHeight()).isGreaterThan(height);
    }

}


