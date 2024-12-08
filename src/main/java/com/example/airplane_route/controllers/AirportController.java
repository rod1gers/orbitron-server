package com.example.airplane_route.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.airplane_route.models.Airport;
import com.example.airplane_route.repositories.AirportRepository;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1/airport")
public class AirportController {

    @Autowired
    private AirportRepository airportRepository;

    @GetMapping("/allAirports")
    public ResponseEntity<List<Airport>> allAirports() {
        List<Airport> airports = airportRepository.findAll();
        return ResponseEntity.ok(airports);
    }

    @PostMapping("/addAirport")
    public ResponseEntity<String> addAirport(@RequestBody Airport airport) {
        Optional<Airport> existingAirport = airportRepository.findByName(airport.getName());
        
        if (existingAirport.isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Airport already exists");
        }

        airportRepository.save(airport);
        return ResponseEntity.ok("Airport added successfully");
    } 
} 

