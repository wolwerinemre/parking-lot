package com.swedbank.sample.parkinglot.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class Vehicle {
    protected String plate;
    protected VehicleType type;
    protected double weight;
    protected double height;

    public abstract boolean canFitInSlot(ParkingSlot slot);
}
