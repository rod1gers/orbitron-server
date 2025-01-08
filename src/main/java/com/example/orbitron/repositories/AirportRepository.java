package com.example.orbitron.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.orbitron.databaseModels.entities.Airport;
import com.example.orbitron.dto.CoordinatesDTO;

import java.util.List;

@Repository
public interface AirportRepository extends JpaRepository<Airport, Long> {
    List<Airport> findByNameContainingIgnoreCase(String name);

    @Query("SELECT new com.example.orbitron.dto.CoordinatesDTO( a.latitude, a.longitude ) FROM Airport a WHERE a.name= :name1 OR a.name= :name2")
    List<CoordinatesDTO> findCoordByName1AndName2(@Param("name1") String name1,@Param("name2") String name2);

    List<Airport> findByCityContainingIgnoreCase(String city);

    List<Airport> findByNameContainingIgnoreCaseOrCityContainingIgnoreCase(String name, String city);
}
