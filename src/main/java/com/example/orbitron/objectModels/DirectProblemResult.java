package com.example.orbitron.objectModels;

import lombok.Getter;

@Getter
public class DirectProblemResult {
    private final double DestinationLatitude;
    private final double DestinationLongitude;
    private final double finalAzimuth; 
    
    public DirectProblemResult(double DestinationLatitude,double DestinationLongitude,double finalAzimuth) {
        this.DestinationLatitude = DestinationLatitude;
        this.DestinationLongitude = DestinationLongitude;
        this.finalAzimuth = finalAzimuth;
    };
}
