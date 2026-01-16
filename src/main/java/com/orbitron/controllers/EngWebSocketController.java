package com.orbitron.controllers;


import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Controller;

import com.orbitron.objectModels.EngineStartRequest;
import com.orbitron.services.ECUSimulator;
import com.orbitron.services.EngineSimulatorInterface;
import com.orbitron.services.OctaveTcpClient;

@Controller
public class EngWebSocketController {
    private final EngineSimulatorInterface engineSimulatorInterface;
    private final OctaveTcpClient octaveTcpClient;
    private final ECUSimulator ecuSimulator;

    public EngWebSocketController(EngineSimulatorInterface engineSimulatorInterface, OctaveTcpClient octaveTcpClient, ECUSimulator ecuSimulator) {
        this.engineSimulatorInterface = engineSimulatorInterface;
        this.octaveTcpClient = octaveTcpClient;
        this.ecuSimulator = ecuSimulator;
    }

    @MessageMapping("/startEngine")
    public void startEngine(@Payload EngineStartRequest request) {
        System.out.println("Engine start triggered for Engine: " + request.getEngineId());

        ecuSimulator.startEngine();
    }

    // @MessageMapping("/connect-engine")
    // public void connectToOctaveEngine() {
    //     System.out.println("Connecting to Octave Engine");

    //     // Call method that connects to engine
    //     octaveTcpClient.connectToEngine();
        

    // }

    
}