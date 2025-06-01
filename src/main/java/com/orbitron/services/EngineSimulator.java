package com.orbitron.services;

import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import com.orbitron.dto.EngineDTO;

@Service
public class EngineSimulator {
    private final SimpMessagingTemplate messagingTemplate;

    public EngineSimulator(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    // The Initial and incremental data here should be mended to fetch from
    // The live sensor data  
    public void startSpoolUp(Long engineId) {
        new Thread(() -> {
            // n1 = %
            // n2 = %
            // egt = degrees celcius
            // fuelFlow = kg/s etc

            double n1 = 0, n2 = 0, egt = 30, fuelFlow = 0;

            while (n1 < 100) {
                n1 += 1;
                n2 += 0.9;
                egt += 2;
                fuelFlow += 0.4;

                EngineDTO dto = new EngineDTO(n1, n2, egt, fuelFlow);
                messagingTemplate.convertAndSend("/topic/engine/" + engineId, dto);
                
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    break;
                }
            }
        }).start();

    }

}
