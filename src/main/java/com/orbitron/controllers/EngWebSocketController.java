package com.orbitron.controllers;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;

import com.orbitron.objectModels.EngineStartRequest;
import com.orbitron.services.EngineSimulator;

@Controller
public class EngWebSocketController {
    private final EngineSimulator engineSimulator;

    public EngWebSocketController(EngineSimulator engineSimulator) {
        this.engineSimulator = engineSimulator;
    }

    @MessageMapping("/start-engine")
    public void startEngine( EngineStartRequest request) {
        System.out.println("Engine start triggered for Engine: " + request.getEngineId());
    
        engineSimulator.startSpoolUp(request.getEngineId());

    }
}