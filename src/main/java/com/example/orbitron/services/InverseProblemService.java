package com.example.orbitron.services;

import org.springframework.stereotype.Service;

import com.example.orbitron.interfaces.GeodesicServiceInterface;
import com.example.orbitron.objectModels.InverseProblemResult;

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

        double geodeticDistance, forwardAzimuth, reverseAzimuth;

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
        double sinAlpha;
        double sinU1 = Math.sin(U1), sinU2 = Math.sin(U2);
        double cosU1 = Math.cos(U1), cosU2 = Math.cos(U2);
        double sinLambda;
        double cosLambda;
        double cosAlphaSqrd;
        double cos2SigmaM = 0;
        double C;
        double tolerance = 1e-12;
        int iterationLimit = 100;
        double uSqrd;
        double A , B;
        double sigmaDiff;

        do {
            sinLambda = Math.sin(lambda);
            cosLambda = Math.cos(lambda);

            sinSigma = Math.sqrt(Math.pow((cosU2 * sinLambda), 2) + Math.pow((cosU1 * sinU2 - sinU1 * cosU2 * cosLambda), 2));
            cosSigma = sinU1 * sinU2 + cosU1 * cosU2 * cosLambda;
            sigma = Math.atan2(sinSigma, cosSigma);

            if (sinSigma == 0) return new InverseProblemResult(0, 0, 0);

            sinAlpha = cosU1 * cosU2 * sinLambda / sinSigma;

            cosAlphaSqrd = 1 - sinAlpha * sinAlpha;

            if ( cosAlphaSqrd != 0 ) {
                cos2SigmaM = cosSigma - ((2 * sinU1 * sinU2) / cosAlphaSqrd);
            }
            
            C = (f / 16) * cosAlphaSqrd * (4 + f * (4 - 3 * cosAlphaSqrd));

            lambdaPrev = lambda;
            lambda = L + (1 - C) * f * sinAlpha * (
                sigma + C * sinSigma * (
                    cos2SigmaM + C * cosSigma * (
                        -1 + 2 * cos2SigmaM * cos2SigmaM
                    )
                )
            );

        } while (Math.abs(lambda - lambdaPrev) > tolerance && --iterationLimit > 0  );

        if (iterationLimit == 0) throw new RuntimeException("Vincenty's formula failed to converge");

        uSqrd = ( cosAlphaSqrd * cosAlphaSqrd ) * (((a * a) - (b * b)) / (b * b)); 
        A = 1 + (uSqrd / 16384) * (4096 + uSqrd * (-768 + uSqrd * (320 - 175 * uSqrd)));
        B = (uSqrd / 1024) * (256 + uSqrd * (-128 + uSqrd * (74 - 47 * uSqrd)));
        sigmaDiff = B * sinSigma * (
                        cos2SigmaM + (1 / 4) * B * (
                            cosSigma * ( -1 + 2 * (cos2SigmaM * cos2SigmaM)) - (1 / 6) * B * cos2SigmaM * (
                                -3 + 4 * (sinSigma * sinSigma)) * (
                                    -3 + 4 * (cos2SigmaM * cos2SigmaM)
                                ) 
                            ) 
                        );

        geodeticDistance = b * A * (sigma  - sigmaDiff);
        forwardAzimuth = Math.atan2(cosU2 * sinLambda, cosU1 * sinU2 - sinU1 * cosU2 * cosLambda);
        reverseAzimuth = Math.atan2(cosU1 * sinLambda, -sinU1 * cosU2 + cosU1 * sinU2 * cosLambda);

        return new InverseProblemResult( geodeticDistance, forwardAzimuth, reverseAzimuth);
    }


}