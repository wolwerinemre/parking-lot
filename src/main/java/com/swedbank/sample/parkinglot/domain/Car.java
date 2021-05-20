package com.swedbank.sample.parkinglot.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class Car extends Vehicle {

    public Car(String plate, double weight, double height) {
        this.plate = plate;
        this.height = height;
        this.weight= weight;
        this.type = VehicleType.CAR;
    }

    @Override
    public boolean canFitInSlot(ParkingSlot slot) {
        return slot.getHeight() >= height;
    }
}
