package com.example.orbitron.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.orbitron.databaseModels.Airport;
import com.example.orbitron.databaseModels.Flight;
import com.example.orbitron.repositories.AirportRepository;
import com.example.orbitron.repositories.FlightRepository;

import java.util.*;

@RestController
@RequestMapping("api/v1/flight")
public class FlightController {
    @Autowired
    private FlightRepository flightRepository;

    @Autowired
    private AirportRepository airportRepository;

    @PostMapping("/saveFlight")
    public ResponseEntity<Map<String, Object>> saveFlight(@RequestBody Flight flight) {
        flightRepository.save(flight);
        List<Airport> departure = airportRepository.findByNameContainingIgnoreCase(flight.getDepartureCity());
        List<Airport> destination = airportRepository.findByNameContainingIgnoreCase(flight.getDestinationCity());
        
        Map<String, Object> response = new HashMap<>();
        response.put("message", "Flight added successfully");
        response.put("departure", departure.get(0) );
        response.put("destination", destination.get(0));
        
        return ResponseEntity.ok(response);
    }
}
