package com.orbitron.controllers;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.orbitron.entities.entities.Airport;
import com.orbitron.repositories.AirportRepository;

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

    @GetMapping("/search")
    public ResponseEntity<List<Airport>> searchAirports(@RequestParam("query") String query) {
        List<Airport> airports = airportRepository.findByNameContainingIgnoreCaseOrCityContainingIgnoreCase( query, query);
        return ResponseEntity.ok(airports);
    } 

    @PostMapping("/addAirport")
    public ResponseEntity<String> addAirport(@RequestBody Airport airport) {
        List<Airport> existingAirport = airportRepository.findByNameContainingIgnoreCase(airport.getName());
        
        if (!existingAirport.isEmpty()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Airport already exists");
        }

        airportRepository.save(airport);
        return ResponseEntity.ok("Airport added successfully");
    } 

    @PostMapping("/addBulkAirports")
    public ResponseEntity<String> addBulkAirports(@RequestBody Airport[] airports) {
        List<Airport> airportList = Arrays.asList(airports);

        airportRepository.saveAll(airportList);
        return ResponseEntity.ok("Airports added successfully!");
    }
} 

