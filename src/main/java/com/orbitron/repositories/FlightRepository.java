package com.orbitron.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.orbitron.entities.entities.Flight;

@Repository
public interface FlightRepository extends JpaRepository<Flight, Long> {
    
}
