package com.swedbank.sample.parkinglot.service;

import com.swedbank.sample.parkinglot.config.properties.ParkingLotProperties;
import com.swedbank.sample.parkinglot.domain.*;
import com.swedbank.sample.parkinglot.dto.ParkingDto;
import com.swedbank.sample.parkinglot.util.exception.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ParkingService {

    private final CostPerMinuteService costPerMinuteService;
    private final ParkingLotProperties parkingLotProperties;

    @Autowired
    public ParkingService(CostPerMinuteService costPerMinuteService,
                          ParkingLotProperties parkingLotProperties) {
        this.costPerMinuteService = costPerMinuteService;
        this.parkingLotProperties = parkingLotProperties;
    }

    @PostConstruct
    private void setup() {
        ParkingLot.getInstance().setParkingLotName("TEST");
        for (int i = 0; i < this.parkingLotProperties.getFloorLevel(); i++) {
            ParkingLot.getInstance().addFloor(
                new ParkingFloor()
                        .setFloorNumber(i + 1)
                        .setOccupiedCount(0)
                        .setSlots(this.createSlots(i + 1))
                        .setHeight(this.parkingLotProperties.getHeight())
                        .setTotalWeight(this.parkingLotProperties.getWeight()));

        }
    }

    private List<ParkingSlot> createSlots(Integer floorNumber) {
        List<ParkingSlot> parkingSlots = new ArrayList<>(this.parkingLotProperties.getSlot());
        for (int i = 0; i < this.parkingLotProperties.getSlot(); i++) {
            parkingSlots.add(new ParkingSlot()
                    .setFloorNumber(floorNumber)
                    .setHeight(this.parkingLotProperties.getHeight())
                    .setSlotNo(i + 1)
                    .setSuitableFor(VehicleType.getSuitableFor(this.parkingLotProperties.getHeight())));
        }
        return parkingSlots;
    }

    public synchronized ParkingDto parkAndCost(final String licensePlate, double weight, double height) {
        Vehicle vehicle = this.createVehicle(licensePlate, weight, height);
        List<ParkingSlot> slots = this.findAvailableSlots(vehicle);
        if(CollectionUtils.isEmpty(slots)) {
            throw new NoAvailableSlotException("There is no slot left to fit for :" + licensePlate);
        } else{
            ParkingSlot slot = this.park(getNearestSlot(slots), vehicle);
            return new ParkingDto()
                    .setCostOfMinute(costPerMinuteService.calculate(height,weight))
                    .setParkingSlot(slot);
        }

    }

    public Boolean unpark(final String licensePlate) {
        ParkingSlot slot = this.getSlot(licensePlate);
        return this.unpark(slot);
    }

    private ParkingSlot getNearestSlot(List<ParkingSlot> slots) {
        return slots.stream().findFirst().orElseThrow();
    }

    private ParkingSlot getSlot(final String licensePlate) {
        return ParkingLot.getInstance().getParkingFloors().stream()
                .map(ParkingFloor::getSlots)
                .flatMap(parkingSlots -> parkingSlots.stream()
                        .filter(parkingSlot -> !parkingSlot.isAvailable() &&
                                parkingSlot.getVehicle().getPlate().equalsIgnoreCase(licensePlate)))
                .findFirst()
                .orElseThrow(() -> new BadRequestException("Vehicle and slot not found :" + licensePlate));
    }

    private Boolean checkVehicleUniqueness(final String licensePlate) {
        return ParkingLot.getInstance().getParkingFloors().stream()
                .map(ParkingFloor::getSlots)
                .flatMap(parkingSlots -> parkingSlots.stream()
                        .filter(parkingSlot -> !parkingSlot.isAvailable() &&
                                parkingSlot.getVehicle().getPlate().equalsIgnoreCase(licensePlate))
                        .map(ParkingSlot::getVehicle))
                .findFirst()
                .isEmpty();
    }

    private ParkingSlot park(ParkingSlot slot, Vehicle vehicle) {
        if(vehicle.canFitInSlot(slot) && this.checkVehicleUniqueness(vehicle.getPlate())){
            slot.assignVehicle(vehicle);
            ParkingLot.getInstance().getParkingFloors().stream()
                    .filter(floor -> floor.getFloorNumber() == slot.getFloorNumber())
                    .findFirst()
                    .ifPresentOrElse(ParkingFloor::addOccupiedCount,
                            () -> new ParkingFloorNotPresentException("Couldnt find the floor with number : " + slot.getFloorNumber()));
        } else {
            throw new ParkingSlotNotFitException("Parking slot is not fit or plate is already registered for :" + vehicle.getPlate());
        }
        return slot;
    }

    private Boolean unpark(ParkingSlot slot) {
        if(!slot.isVacant()){
            slot.removeVehicle();
            ParkingLot.getInstance().getParkingFloors().stream()
                    .filter(floor -> floor.getFloorNumber() == slot.getFloorNumber())
                    .findFirst()
                    .ifPresentOrElse(ParkingFloor::removeOccupiedCount,
                            () -> new ParkingFloorNotPresentException("Couldnt find the floor with number : " + slot.getFloorNumber()));
            return true;
        }
        return false;
    }

    private List<ParkingSlot> findAvailableSlots(Vehicle vehicle) {
        return ParkingLot.getInstance().getParkingFloors()
                .stream()
                .filter(parkingFloor -> parkingFloor.isAvailable(vehicle.getHeight(), vehicle.getWeight()))
                .map(ParkingFloor::getSlots)
                .flatMap(parkingSlots -> parkingSlots.stream()
                        .filter(ParkingSlot::isAvailable)
                        .filter(parkingSlot -> parkingSlot.canFitVehicle(vehicle))
                        .sorted(Comparator.comparingInt(ParkingSlot::getSlotNo)))
                .collect(Collectors.toList());
    }

    private Vehicle createVehicle(String plate, double weight, double height) {
        VehicleType type = VehicleType.getTypeFormHeight(height);
        switch (type){
            case MOTORCYCLE:
                return new Motorcycle(plate, weight, height);
            case CAR:
                return new Car(plate, weight, height);
            case BUS:
                return new Bus(plate, weight, height);
            default:
                throw new VehicleTypeNotPresentException("Couldnt find vehicle type: " + type);
        }
    }
}
