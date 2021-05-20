package com.swedbank.sample.parkinglot.domain;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.List;

@Data
@Accessors(chain = true)
@RequiredArgsConstructor
public class ParkingFloor implements Comparable<ParkingFloor>{

    private long floorNumber;
    private List<ParkingSlot> slots = new ArrayList<>();
    private long occupiedCount;
    private Double totalWeight;
    private Double height;

    public void addSlot(ParkingSlot slot) {
        slots.add(slot);
    }

    public void removeSlot(ParkingSlot slot) {
        slots.remove(slot);
    }

    public void addOccupiedCount() {
        this.occupiedCount++;
    }

    public void removeOccupiedCount() {
        this.occupiedCount--;
    }

    public long getFreeSlotCount() {
        return slots.size() - occupiedCount;
    }

    public boolean isAvailable(double vehicleHeight, double vehicleWeight) {
        Double occupiedWeight = slots.stream()
                .filter(parkingSlot -> !parkingSlot.isAvailable())
                .map(parkingSlot -> parkingSlot.getVehicle())
                .mapToDouble(Vehicle::getWeight)
                .sum();
        return getFreeSlotCount() > 0 &&
                totalWeight >= occupiedWeight + vehicleWeight &&
                height >= vehicleHeight;
    }

    @Override
    public int compareTo(ParkingFloor o) {
        if (o.getFloorNumber() > getFloorNumber())
            return 1;
        else if (o.getFloorNumber() < getFloorNumber())
            return -1;
        return 0;
    }
}
