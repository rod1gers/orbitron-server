package com.example.orbitron.databaseModels.entities;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "all_airports")
@Data
public class Airport {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private double latitude;
    private double longitude;
    private String name;
    private String city;
    private String IATA;
}
