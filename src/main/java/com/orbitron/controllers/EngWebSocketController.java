package com.orbitron.controllers;


import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;

import com.orbitron.objectModels.EngineStartRequest;
import com.orbitron.services.EngineSimulator;
import com.orbitron.services.OctaveTcpClient;

@Controller
public class EngWebSocketController {
    private final EngineSimulator engineSimulator;
    private final OctaveTcpClient octaveTcpClient;

    public EngWebSocketController(EngineSimulator engineSimulator, OctaveTcpClient octaveTcpClient) {
        this.engineSimulator = engineSimulator;
        this.octaveTcpClient = octaveTcpClient;
    }

    @MessageMapping("/start-engine")
    public void startEngine( EngineStartRequest request) {
        System.out.println("Engine start triggered for Engine: " + request.getEngineId());
    
        engineSimulator.startSpoolUp(request.getEngineId());

    }
}