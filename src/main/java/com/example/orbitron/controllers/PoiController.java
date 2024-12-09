package com.example.orbitron.controllers;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.orbitron.databaseModels.Poi;
import com.example.orbitron.repositories.PoiRepository;

@RestController
@RequestMapping("api/v1/poi")
public class PoiController {
    @Autowired
    private PoiRepository poiRepository;

    @PostMapping("/addPoi")
    public ResponseEntity<String> addPoi(@RequestBody List<Poi> poi) {
        List<String> poiNames = poi.stream()
                                    .map(Poi::getName)
                                    .collect(Collectors.toList());

        List<Poi> existingPois = poiRepository.findByNameIn(poiNames);
        if (!existingPois.isEmpty()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Point of Interest exists!");
        }
        
        poiRepository.saveAll(poi);
        return ResponseEntity.ok("Point of Interest added successfully!");
    }

    @GetMapping("/getAllPois")
    public ResponseEntity<List<Poi>> getAllPois() {
        List<Poi> response = poiRepository.findAll();
        return ResponseEntity.ok(response);
    }
}
