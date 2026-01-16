package com.orbitron.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.orbitron.dto.CoordinatesDTO;
import com.orbitron.entities.entities.Airport;
import com.orbitron.entities.entities.Flight;
import com.orbitron.objectModels.DirectProblemResult;
import com.orbitron.objectModels.InverseProblemResult;
import com.orbitron.repositories.AirportRepository;
import com.orbitron.repositories.FlightRepository;

import lombok.extern.slf4j.Slf4j;

import java.util.*;

@RestController
@RequestMapping("api/v1/flight")
@Slf4j
public class FlightController {
    private FlightRepository flightRepository;
    private AirportRepository airportRepository;
    
    @Autowired
    public FlightController(FlightRepository flightRepository, AirportRepository airportRepository) {
        this.flightRepository = flightRepository;
        this.airportRepository = airportRepository;
    }

    @PostMapping("/saveFlight")
    public ResponseEntity<Map<String, Object>> saveFlight(@RequestBody Flight flight) {
        flightRepository.save(flight);
        List<Airport> departure = airportRepository.findByNameContainingIgnoreCase(flight.getDepartureAirport());
        List<Airport> destination = airportRepository.findByNameContainingIgnoreCase(flight.getDestinationAirport());
        
        String airport1 = flight.getDepartureAirport();
        String airport2 = flight.getDestinationAirport();

        List<CoordinatesDTO> coordinates = airportRepository.findCoordByName1AndName2( airport1, airport2);
        
        double lat1 = coordinates.get(0).getLatitude();
        double lon1 = coordinates.get(0).getLongitude();
        double lat2 = coordinates.get(1).getLatitude();
        double lon2 = coordinates.get(1).getLongitude();

        // InverseProblemResult inverseProblemResult = inverseProblemService.calculateInverse(lat1, lon1, lat2, lon2);        

        Map<String, Object> response = new HashMap<>();
        response.put("message", "Flight added successfully");
        response.put("departure", departure.get(0) );
        response.put("destination", destination.get(0));
        // response.put("inverseProblemResult", inverseProblemResult);
        
        return ResponseEntity.ok(response);
    }

    // @GetMapping("/tryFetchingCoordinates")
    // public ResponseEntity<List<CoordinatesDTO>> fetchCoordinates(@RequestParam("departure") String name1,@RequestParam("destination") String name2) {
    //     List<CoordinatesDTO> response = airportRepository.findCoordByName1AndName2(name1, name2);

    //     return ResponseEntity.ok(response);
    // }

}
