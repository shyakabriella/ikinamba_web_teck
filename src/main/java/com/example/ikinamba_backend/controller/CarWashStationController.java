package com.example.ikinamba_backend.controller;

import com.example.ikinamba_backend.model.CarWashStation;
import com.example.ikinamba_backend.repository.CarWashStationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/carwashstations")
public class CarWashStationController {

    @Autowired
    private CarWashStationRepository carWashStationRepository;

    // Endpoint to get all car wash stations
    @GetMapping
    public List<CarWashStation> getAllCarWashStations() {
        return carWashStationRepository.findAll();
    }

    // Endpoint to create a new car wash station
    @PostMapping
    public ResponseEntity<CarWashStation> createCarWashStation(@RequestBody CarWashStation carWashStation) {
        CarWashStation savedCarWashStation = carWashStationRepository.save(carWashStation);
        return ResponseEntity.ok(savedCarWashStation);
    }
}
