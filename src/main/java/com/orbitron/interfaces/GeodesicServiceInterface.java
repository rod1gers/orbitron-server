package com.orbitron.interfaces;

import com.orbitron.objectModels.InverseProblemResult;

public interface GeodesicServiceInterface {
    // Distance should be given in meters
    InverseProblemResult calculateInverse( double lat1, double lon1, double lat2, double lon2 );
    
}
