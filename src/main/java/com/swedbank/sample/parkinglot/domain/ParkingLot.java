package com.swedbank.sample.parkinglot.domain;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.List;

@Data
@Accessors(chain = true)
public class ParkingLot {
    private String parkingLotName;
    private List<ParkingFloor> parkingFloors = new ArrayList<>();

    private static ParkingLot parkingLot = null;

    //singleton
    private ParkingLot() {
    }

    public static ParkingLot getInstance() {
        if (parkingLot == null) {
            parkingLot = new ParkingLot();
        }
        return parkingLot;
    }

    public void addFloor(ParkingFloor floor) {
        parkingFloors.add(floor);
    }

    public void deleteFloor(ParkingFloor floor) {
        if(!parkingFloors.isEmpty()) {
            parkingFloors.remove(floor);
        }
    }
}
