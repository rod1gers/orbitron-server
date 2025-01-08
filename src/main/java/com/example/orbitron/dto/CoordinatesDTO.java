package com.example.orbitron.dto;

import lombok.Getter;

@Getter
public class CoordinatesDTO {
    private final double latitude;
    private final double longitude;

    CoordinatesDTO(double latitude, double longitude ) {
        this.latitude = latitude;
        this.longitude = longitude;
    }
}
