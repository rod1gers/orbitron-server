package com.example.airplane_route.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.airplane_route.models.Airport;

@Repository
public interface AirportRepository extends JpaRepository<Airport, Long> {
    
}
