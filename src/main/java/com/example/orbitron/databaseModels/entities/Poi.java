package com.example.orbitron.databaseModels.entities;

import jakarta.persistence.*;
import java.util.UUID;

import com.example.orbitron.components.AltitudeDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import lombok.Data;

@Entity
@Data
@Table(name = "POIs")
public class Poi {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID poi_id;

    private String name;
    private String description;
    
    @Enumerated(EnumType.STRING)
    private Category category;
    
    private double latitude;
    private double longitude;

    @JsonDeserialize(using = AltitudeDeserializer.class)
    private Double altitude;

    private String country;
    private String city;

    public enum Category {
        LANDMARK,
        CITY,
        NATURAL_SITE,
        MONUMENT,
        HISTORICAL_SITE,
        CULTURAL_SITE,
        ARTIFICIAL_SITE,
        WATER_BODY,
        MOUNTAIN_RANGE,
        VOLCANO
    }
}
