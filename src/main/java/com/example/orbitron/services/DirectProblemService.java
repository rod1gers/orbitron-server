package com.example.orbitron.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.orbitron.objectModels.InverseProblemResult;


@Service
public class DirectProblemService {
    private static final double EARTH_RADIUS = 6378137.0;

    // Method to calculate the new coordinates along the great circle
    public static List<double[]> calculateIntermediaryPoints(
        double initLatitude, double initLongitude, 
        double initBearing, double totalDistance,
        double endLatitude, double endLongitude    
    ) {
        List<double[]> coordinates = new ArrayList<>();

        // Convert degrees to radians
        double lat1 = Math.toRadians(initLatitude);
        double lon1 = Math.toRadians(initLongitude);
        double bearing = Math.toRadians(initBearing);

        // Calculate the number of steps (for plotting)
        int numSteps = 100;
        double stepDistance = totalDistance / numSteps;

        // Loop through the path
        for (int i = 0; i <= numSteps; i++) {
            double distance = i * stepDistance;

            // Using the great circle formula
            double angularDistance = distance / EARTH_RADIUS;
            double lat2 = Math.asin(Math.sin(lat1) * Math.cos(angularDistance) +
                    Math.cos(lat1) * Math.sin(angularDistance) * Math.cos(bearing));

            double lon2 = lon1 + Math.atan2(Math.sin(bearing) * Math.sin(angularDistance) * Math.cos(lat1),
                    Math.cos(angularDistance) - Math.sin(lat1) * Math.sin(lat2));

            // Convert radians back to degrees
            coordinates.add(new double[]{Math.toDegrees(lat2), Math.toDegrees(lon2)});
        }

        // Verify if the calculated last point is close to the provided end point using Vincenty's formula
        double[] lastCoord = coordinates.get(coordinates.size() - 1);
        InverseProblemResult distanceToEnd = new InverseProblemService().calculateInverse(lastCoord[0], lastCoord[1], endLatitude, endLongitude);
        double distance = distanceToEnd.getGeodesicDistance();

        if (distance > 1000) { // Set threshold for acceptable distance
            System.out.println("Warning: The calculated end point is more than 1000 meters from the expected end point.");
        } else {
            System.out.println("The calculated end point is within an acceptable range of the expected end point.");
        }

        return coordinates;
    }

}


    



