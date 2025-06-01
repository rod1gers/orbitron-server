package com.orbitron.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.orbitron.entities.entities.Engine;

public interface EngineRepository extends JpaRepository<Engine, Long> {
    Optional<Engine> findById(Long id);

}
