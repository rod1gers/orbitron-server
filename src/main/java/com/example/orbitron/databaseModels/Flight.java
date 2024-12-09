package com.example.orbitron.databaseModels;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name= "flights")
@Data
public class Flight {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String departureCity;
    private String destinationCity;

}
