package com.example.orbitron.databaseModels.entities;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name="flight_passengers")
@Data
public class FlightPassenger {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    private String fullName;

    @ManyToOne
    @JoinColumn(name= "flight_id", referencedColumnName = "id")
    private Flight flightId;

    private String email;
    private String phoneNumber;

    private String seat;

}
