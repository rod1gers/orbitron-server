package com.example.orbitron.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.orbitron.databaseModels.Airport;

import java.util.List;

@Repository
public interface AirportRepository extends JpaRepository<Airport, Long> {
    List<Airport> findByNameContainingIgnoreCase(String name);
    List<Airport> findByCityContainingIgnoreCase(String city);

    List<Airport> findByNameContainingIgnoreCaseOrCityContainingIgnoreCase(String name, String city);
}
