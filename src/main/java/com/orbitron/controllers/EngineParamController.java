package com.orbitron.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.orbitron.dto.ThrottleMsgDTO;
import com.orbitron.entities.entities.Engine;
import com.orbitron.repositories.EngineRepository;
import com.orbitron.services.ECUSimulator;
import com.orbitron.services.EngineSimulator;
import com.orbitron.services.OctaveTcpClient;

@RestController
@RequestMapping("/api/v1/engine")
public class EngineParamController {

    private final EngineRepository engineRepository;
    private final EngineSimulator engineSimulator;
    private final ECUSimulator ecuSimulator;
    private final OctaveTcpClient octaveTcpClient;

    public EngineParamController
    (
        EngineSimulator engineSimulator,
        EngineRepository engineRepository, 
        ECUSimulator ecuSimulator,
        OctaveTcpClient octaveTcpClient
    ) 
    {
        this.engineSimulator = engineSimulator;
        this.engineRepository = engineRepository;
        this.ecuSimulator = ecuSimulator;
        this.octaveTcpClient = octaveTcpClient;
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

    @MessageMapping("/startEngine")
    public void startEngine(@RequestParam Long engineId) {
        engineSimulator.startSpoolUp(engineId);

        
    }

    @MessageMapping("/throttle")
    public void handleThrottle(ThrottleMsgDTO message) {
        // Handle the throttle lever position change
        // Send the throttle position to the ECU 
        int throttlePosition = message.getThrottlePosition();
        Long engineId = Long.valueOf(message.getEngineId());
        
        ecuSimulator.setEngineId(engineId);
        ecuSimulator.setThrottleTarget(throttlePosition);
        ecuSimulator.update();
    }

    @PostMapping("/testOctave")
    public ResponseEntity<String> contactOctave() {
        // Send test data to octave
        octaveTcpClient.sendDataToOctave();
        return ResponseEntity.ok("Sent to octave");
    }

    // Add method to set parameters
    // @PutMapping("/updateEngineParams")
    // public ResponseEntity<> setEngineParam() {

    // }

}
