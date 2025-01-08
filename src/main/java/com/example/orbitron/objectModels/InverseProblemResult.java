package com.example.orbitron.objectModels;

public class InverseProblemResult {
    private final double geodesicDistance;
    private final double forwardAzimuth;
    private final double reverseAzimuth;

    public InverseProblemResult(double geodesicDistance, double forwardAzimuth, double reverseAzimuth ) {
        this.geodesicDistance = geodesicDistance;
        this.forwardAzimuth = forwardAzimuth;
        this.reverseAzimuth = reverseAzimuth;
    }

    public double getGeodesicDistance() {
        return geodesicDistance;
    }

    public double getForwardAzimuth() {
        return forwardAzimuth;
    }

    public double getReverseAzimuth() {
        return reverseAzimuth;
    }
}
