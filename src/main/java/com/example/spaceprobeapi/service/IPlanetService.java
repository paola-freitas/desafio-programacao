package com.example.spaceprobeapi.service;

import com.example.spaceprobeapi.dto.PlanetDto;
import com.example.spaceprobeapi.model.Planet;

public interface IPlanetService {

    Long createPlanet(PlanetDto planet);

    Planet getPlanetById(Long id);
}
