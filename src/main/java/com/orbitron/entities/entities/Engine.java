package com.orbitron.entities.entities;

import com.orbitron.types.EngineState;

import jakarta.persistence.*;
import lombok.Data;


@Entity
@Table(name= "engine")
@Data
public class Engine {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; 
    
    // Core Parameters
    private double n1;
    private double n2;
    // Exhaust Gas Temp
    private double egt; 
    private double fuelFlow;


    // Control Inputs
    private double throttlePosition;

    // Secondary parameters
    private double oilPressure;
    private double oilTemp;
    private double vibration;

    // Engine status 
    @Enumerated(EnumType.STRING)
    private EngineState state;


}
