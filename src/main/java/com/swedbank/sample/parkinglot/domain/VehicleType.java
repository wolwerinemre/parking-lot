package com.swedbank.sample.parkinglot.domain;

import com.swedbank.sample.parkinglot.util.exception.VehicleTypeNotPresentException;
import lombok.Getter;

import java.util.EnumSet;

@Getter
public enum VehicleType {
    MOTORCYCLE(100.0), BUS(1000.0), CAR(400.0); //sample props
    private double maxHeight;

    VehicleType(double maxHeight) {
        this.maxHeight = maxHeight;
    }

    public static EnumSet getSuitableFor(double maxHeight) {
        if(VehicleType.MOTORCYCLE.getMaxHeight() >= maxHeight){
            return EnumSet.of(VehicleType.MOTORCYCLE);
        } else if(VehicleType.CAR.getMaxHeight() >= maxHeight && VehicleType.MOTORCYCLE.getMaxHeight() < maxHeight){
            return EnumSet.of(VehicleType.MOTORCYCLE, VehicleType.CAR);
        } else if(VehicleType.BUS.getMaxHeight() >= maxHeight){
            return EnumSet.of(VehicleType.MOTORCYCLE, VehicleType.CAR, VehicleType.BUS);
        } else {
            throw new VehicleTypeNotPresentException("There is no suitable vehicle type for given maxheight :" + maxHeight);
        }
    }

    public static VehicleType getTypeFormHeight(double height) {
        if(VehicleType.MOTORCYCLE.getMaxHeight() >= height){
            return VehicleType.MOTORCYCLE;
        } else if(VehicleType.CAR.getMaxHeight() >= height && VehicleType.MOTORCYCLE.getMaxHeight() < height){
            return VehicleType.CAR;
        } else if(VehicleType.BUS.getMaxHeight() >= height){
            return VehicleType.BUS;
        } else {
            throw new VehicleTypeNotPresentException("There is no suitable vehicle type for given height :" + height);
        }
    }
}

