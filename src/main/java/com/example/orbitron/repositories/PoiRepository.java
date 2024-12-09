package com.example.orbitron.repositories;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.orbitron.databaseModels.Poi;

@Repository
public interface PoiRepository extends JpaRepository<Poi, UUID > {
    List<Poi> findByCategory(String category);
    List<Poi> findByNameIn(List<String> name);
}
