package com.swedbank.sample.parkinglot.service;

import com.swedbank.sample.parkinglot.config.properties.ParkingLotProperties;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
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
public class CostServiceTests {

    @Autowired
    private ParkingLotProperties parkingLotProperties;
    private CostPerMinuteService costPerMinuteService;

    @BeforeEach
    void init() {
        costPerMinuteService = new CostPerMinuteService(parkingLotProperties);
    }

    @Test
    void test_calculate(){
        BigDecimal result = costPerMinuteService.calculate(100, 100);
        Assertions.assertThat(BigDecimal.valueOf(0.17d)).isEqualTo(result);
    }
}
