package com.example.orbitron.services;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.orbitron.objectModels.InverseProblemResult;

@SpringBootTest
public class InverseProblemTests {
    
    @Test
    public void TestConvergence() {
        double lat1 = 1.0;
        double lon1 = 1.0;
        double lat2 = 2.0;
        double lon2 = 2.0;

        InverseProblemService inverseProblemService = new InverseProblemService();
        InverseProblemResult result = inverseProblemService.calculateInverse(lat1, lon1, lat2, lon2);

        assertTrue(result.getGeodesicDistance() > 0, "Distance should be greater than zero");
    }

    
}
