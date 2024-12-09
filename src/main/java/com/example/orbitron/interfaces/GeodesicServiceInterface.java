package com.example.orbitron.interfaces;

import com.example.orbitron.models.InverseProblemResult;

public interface GeodesicServiceInterface {
    // Distance should be given in meters
    InverseProblemResult calculateInverse( double lat1, double lon1, double lat2, double lon2 );
}
