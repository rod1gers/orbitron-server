package com.example.orbitron.services;

import org.springframework.stereotype.Service;

import com.example.orbitron.interfaces.GeodesicServiceInterface;
import com.example.orbitron.models.InverseProblemResult;

@Service
public class InverseProblemService implements GeodesicServiceInterface {

    private static final double WGS84_A = 6378137.0;
    private static final double WGS84_F = 1 / 298.257223563;
    private static final double WGS84_B = WGS84_A * (1 - WGS84_F);

    @Override
    public InverseProblemResult calculateInverse(double lat1, double lon1, double lat2, double lon2) {
        double a = WGS84_A;
        double b = WGS84_B;
        double f = WGS84_F;

        double phi1 = Math.toRadians(lat1);
        double phi2 = Math.toRadians(lat2);
        double lambda1 = Math.toRadians(lon1);
        double lambda2 = Math.toRadians(lon2); 

        double L = lambda2 - lambda1;
        double U1 = Math.atan((1 - f) * Math.tan(phi1));
        double U2 = Math.atan((1 - f) * Math.tan(phi2));

        double lambda = L;
        double lambdaPrev;
        double sinSigma, cosSigma, sigma;
        double sinAlpha, cosAlpha;
        double sinU1 = Math.sin(U1), sinU2 = Math.sin(U2);
        double cosU1 = Math.cos(U1), cosU2 = Math.cos(U2);
        double sinLambda = Math.sin(lambda);
        double cosLambda = Math.cos(lambda);
        double sinLambdaSqrd = Math.pow(sinLambda, 2);
        double cosLambdaSqrd = Math.pow(cosLambda, 2);
        double cos2SigmaM;
        double C;
        double tolerance = 1e-12;
        int iterationLimit = 100;

        do {
            sinSigma = Math.sqrt(Math.pow((cosU2 * sinLambda), 2) + Math.pow((cosU1 * sinU2 - sinU1 * cosU2 * cosLambda), 2));
            cosSigma = sinU1 * sinU2 + cosU1 * cosU2 * cosLambda;
            sigma = Math.atan2(sinSigma, cosSigma);

            if (sinSigma == 0) return new InverseProblemResult(0, 0, 0);

            sinAlpha = cosU1 * cosU2 * sinLambda / sinSigma;

            cosLambdaSqrd = 1 - sinLambdaSqrd;

            cos2SigmaM = cosSigma - ((2 * sinU1 * sinU2) / cosLambdaSqrd);
            C = (f / 16) * cosLambdaSqrd * (4 + f * (4 - 3 * cosLambdaSqrd));

            lambdaPrev = lambda;
            lambda = L + (1 - C) * f * sinAlpha * (sigma + C * sinSigma * (cos2SigmaM) + C * cosSigma * (-1 + 2 * Math.pow(cos2SigmaM, 2)));

        } while (Math.abs(lambda - lambdaPrev) < tolerance && --iterationLimit > 0  );

        if (iterationLimit == 0) throw new RuntimeException("Vincenty's formula failed to converge");

        // Replace the values passed coz they are just for testing
        return new InverseProblemResult(lambda, lambdaPrev, iterationLimit);

    }


}