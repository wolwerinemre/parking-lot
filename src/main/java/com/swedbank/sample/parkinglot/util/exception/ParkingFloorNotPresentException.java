package com.swedbank.sample.parkinglot.util.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class ParkingFloorNotPresentException extends RuntimeException {
    public ParkingFloorNotPresentException(String message) {
        super(message);
    }
}
