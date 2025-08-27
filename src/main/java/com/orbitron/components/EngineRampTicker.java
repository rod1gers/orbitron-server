package com.orbitron.components;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.orbitron.services.ECUSimulator;

@Component
public class EngineRampTicker {
    private ECUSimulator ecuSimulator;
    
    public EngineRampTicker(ECUSimulator ecuSimulator) {
        this.ecuSimulator = ecuSimulator;
    }

    @Scheduled(fixedRate = 100)
    public void tick() {
        ecuSimulator.update();

        // Publish engine state to the topic that ECAM is listening to
    }
}
