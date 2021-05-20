package com.swedbank.sample.parkinglot.controller;

import com.swedbank.sample.parkinglot.domain.Motorcycle;
import com.swedbank.sample.parkinglot.domain.ParkingSlot;
import com.swedbank.sample.parkinglot.dto.ParkingDto;
import com.swedbank.sample.parkinglot.service.ParkingService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.ArgumentMatchers.anyString;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class ParkingControllerTests {

    @Mock
    private ParkingService parkingService;
    private ParkingController parkingController;

    @BeforeEach
    void init() {
        parkingController = new ParkingController(parkingService);
    }

    @Test
    void test_parkAndCost(){
        String plate = "Test123";
        Double weight = 100d;
        Double height = 100d;
        ParkingDto parkingDto = new ParkingDto()
                .setCostOfMinute(BigDecimal.ONE)
                .setParkingSlot(new ParkingSlot()
                        .setSlotNo(1)
                        .setFloorNumber(1)
                        .setHeight(height)
                        .setVacant(true)
                        .setVehicle(new Motorcycle(plate, weight, height)));
        Mockito.when(parkingService.parkAndCost(anyString(), anyDouble(), anyDouble())).thenReturn(parkingDto);
        ParkingDto result = parkingController.parkAndCost(weight, height, plate).getBody();
        Mockito.verify(parkingService, Mockito.times(1)).parkAndCost(anyString(), anyDouble(), anyDouble());
        Assertions.assertThat(parkingDto.getCostOfMinute()).isEqualTo(result.getCostOfMinute());
        Assertions.assertThat(parkingDto.getParkingSlot().getFloorNumber()).isEqualTo(result.getParkingSlot().getFloorNumber());
        Assertions.assertThat(parkingDto.getParkingSlot().getVehicle().getPlate()).isEqualTo(result.getParkingSlot().getVehicle().getPlate());
    }

    @Test
    void test_unpark(){
        String plate = "Test123";
        Mockito.when(parkingService.unpark(anyString())).thenReturn(Boolean.TRUE);
        Boolean result = parkingController.unpark(plate).getBody();
        Mockito.verify(parkingService, Mockito.times(1)).unpark(anyString());
        Assertions.assertThat(result).isTrue();
    }
}
