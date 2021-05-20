package com.swedbank.sample.parkinglot.util.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class VehicleTypeNotPresentException extends RuntimeException {
    public VehicleTypeNotPresentException(String message) {
        super(message);
    }
}
