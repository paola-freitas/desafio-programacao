package com.example.spaceprobeapi.repository;

import com.example.spaceprobeapi.model.Planet;
import com.example.spaceprobeapi.model.SpaceProbe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ISpaceProbeRepository extends JpaRepository<SpaceProbe, Long> {

    List<SpaceProbe> findByPositionXAndPositionYAndPlanet(int x, int y, Planet planet);

}
