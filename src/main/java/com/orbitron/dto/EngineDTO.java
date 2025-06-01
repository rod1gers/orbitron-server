package com.orbitron.dto;

import lombok.Getter;

@Getter
public class EngineDTO {
    private final double n1, n2, egt, fuelFlow;

    public EngineDTO(double n1, double n2, double egt, double fuelFlow) {
        this.n1 = n1;
        this.n2 = n2;
        this.egt = egt;
        this.fuelFlow = fuelFlow;
    }
}
