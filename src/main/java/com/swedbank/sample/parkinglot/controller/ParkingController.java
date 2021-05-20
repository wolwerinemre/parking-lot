package com.swedbank.sample.parkinglot.controller;

import com.swedbank.sample.parkinglot.dto.ParkingDto;
import com.swedbank.sample.parkinglot.service.ParkingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@RestController
@RequestMapping(value = "/parking")
@Validated
@CrossOrigin(exposedHeaders = "Access-Control-Allow-Origin")
public class ParkingController {

    private final ParkingService parkingService;

    @Autowired
    public ParkingController(ParkingService parkingService) {
        this.parkingService = parkingService;
    }

    @GetMapping("/parkAndCost")
    public @ResponseBody
    ResponseEntity<ParkingDto> parkAndCost(@RequestParam @Valid @NotNull @Positive double weight,
                                           @RequestParam @Valid @NotNull @Positive double height,
                                           @RequestParam @Valid @NotBlank String plate){
        return new ResponseEntity<>(parkingService.parkAndCost(plate, weight, height),
                HttpStatus.OK);
    }

    @GetMapping("/unpark")
    public @ResponseBody
    ResponseEntity<Boolean> unpark(@RequestParam @Valid @NotBlank String plate){
        return new ResponseEntity<>(parkingService.unpark(plate),
                HttpStatus.OK);
    }
}
