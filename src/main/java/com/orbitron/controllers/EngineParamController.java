package com.orbitron.controllers;

import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.orbitron.entities.entities.Engine;
import com.orbitron.repositories.EngineRepository;
import com.orbitron.services.EngineSimulator;

@RestController
@RequestMapping("/api/v1/engine")
public class EngineParamController {

    private final EngineRepository engineRepository;
    private final EngineSimulator engineSimulator;

    public EngineParamController(EngineSimulator engineSimulator, EngineRepository engineRepository) {
        this.engineSimulator = engineSimulator;
        this.engineRepository = engineRepository;
    }

    
    @GetMapping("/{id}")
    public ResponseEntity<Engine> getEngineParam(@PathVariable Long id) {
        Optional<Engine> engine = engineRepository.findById(id);
        
        if (engine.isPresent()) {
            return ResponseEntity.ok(engine.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/addEngine")
    public ResponseEntity<String> addEngine(@RequestBody Engine engine ) {
        Engine savedEngine = engineRepository.save(engine);
        
        if (savedEngine.getId() != null) {
            return ResponseEntity.ok("Engine saved");
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body("Error saving engine");
        }
    }

    @PostMapping("/startEngine")
    public ResponseEntity<String> startEngine(@RequestParam Long engineId) {
        engineSimulator.startSpoolUp(engineId);

        // Return data constantly via the websocket
        return ResponseEntity.ok("Spool-up started successfully");
    }

    // Add method to set parameters
    // @PutMapping("/updateEngineParams")
    // public ResponseEntity<> setEngineParam() {

    // }

}
