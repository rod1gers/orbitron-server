package com.example.orbitron.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.orbitron.databaseModels.entities.Flight;

@Repository
public interface FlightRepository extends JpaRepository<Flight, Long> {
    
}
