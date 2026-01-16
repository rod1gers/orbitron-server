package com.orbitron.services;

import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import java.lang.Long;
import com.orbitron.dto.EngineDTO;

import lombok.Data;

@Data
@Service
public class ECUSimulator {
    private final SimpMessagingTemplate messagingTemplate;
    private final OctaveTcpClient octaveTcpClient;
    private Long engineId;

    public ECUSimulator(SimpMessagingTemplate messagingTemplate, OctaveTcpClient octaveTcpClient) {
        this.messagingTemplate = messagingTemplate;
        this.octaveTcpClient = octaveTcpClient;
    }

    // The throttlevalues need to flow in a stream as it enters
    private int targetThrottle;
    private int currentThrottle;
    private double fuelFlow;
    private double n1 = 0.0;
    private double n2 = 0.0;
    private double egt = 0.0;

    private final double n1RampRate = 0.05;
    private final double n2RampRate = 0.03;

    private final int throttleStep = 1;

    // Set throttle value within 100%
    public void setThrottleTarget(int value) {
        this.targetThrottle = Math.max(0, Math.min(100, value));
    }

    // The currentThrottle will gradually rise till it arrives at the target throttleValue
    public void updateThrottleRamp() {
        if (currentThrottle < targetThrottle) {
            currentThrottle = Math.min(currentThrottle + throttleStep, targetThrottle);
        } else if (currentThrottle > targetThrottle) {
            currentThrottle = Math.max(currentThrottle - throttleStep, targetThrottle);
        }
    }
    

    // ON ENGINE START SWITCH
    // Open the starter air valve
    // In the engine, the starter air valve should be open
    // Command Octave engine to open starter air valve and provide bleed air
    public void startEngine() {
        octaveTcpClient.startEngine();
    }
    
    public void openStarterAirValve() {
        // Instruct Engine to start spool up by opening the start air valve
        octaveTcpClient.openStarterAirValve();

    }

    // ON THRUST LEVER
    // Calculate the thrust and power setting corresponding to the lever position
    // Increase the flow of fuel to the engine based on the factors such as air flow, altitude, Air Density, EGT, etc...
    public void calculateThrustSettings(Long engineId) {
        // Calculate:
        // - Fuel Flow in every ramp of currentThrust
        double throttleFraction = currentThrottle / 100.0 ;
        double fuelFlowMax = 0.8;  // Max fuel flow in kg/s
        double fuelFlowMin = 0.05;  // Idle flow

        fuelFlow = fuelFlowMax * throttleFraction + fuelFlowMin;


        // - Engine spool speeds (N1/N2)
        double n1Target = 15 + (85 * throttleFraction);
        double n2Target = 55 + (45 * throttleFraction);

        n1 += n1RampRate * (n1Target - n1);
        n2 += n2RampRate * (n2Target - n2);


        // - Compressor vane Positions

        // - Variable stator vanes ( if equipped)

        // - Bleed valve positions

        // - EGT temperature 
        double baseEGT = 400; // °C, idle exhaust temp
        double fuelToTempFactor = 700; // °C per kg/s of fuel
        double coolingFactor = 1.2; // °C reduction per % of N2

        egt = baseEGT + (fuelFlow * fuelToTempFactor) - (n2 * coolingFactor);

        // Clamp EGT to realistic range
        egt = Math.max(400, Math.min(egt, 1100));

        EngineDTO engineDTO = new EngineDTO(n1, n2, egt, fuelFlow);
        messagingTemplate.convertAndSend("/topic/engine/" + engineId ,engineDTO);
    }
    
    
    public void update() {
        updateThrottleRamp();
        calculateThrustSettings(engineId);
    }

}
