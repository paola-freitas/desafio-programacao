package com.example.spaceprobeapi.repository;

import com.example.spaceprobeapi.model.Planet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IPlanetRepository extends JpaRepository<Planet, Long> {
}
