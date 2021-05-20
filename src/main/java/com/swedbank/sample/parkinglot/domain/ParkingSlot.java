package com.swedbank.sample.parkinglot.domain;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Set;

@Data
@Accessors(chain = true)
public class ParkingSlot {
    private int floorNumber;
    private double height;
    private int slotNo;
    private boolean vacant;
    private Vehicle vehicle;
    private Set<VehicleType> suitableFor;

    public boolean isAvailable() {
        return vehicle == null;
    }

    public boolean assignVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
        vacant = false;
        return true;
    }

    public boolean removeVehicle() {
        this.vehicle = null;
        vacant = true;
        return false;
    }

    public boolean canFitVehicle(Vehicle vehicle) {
        return isAvailable() && vehicle.canFitInSlot(this);
    }
}
