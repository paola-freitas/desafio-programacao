package com.example.spaceprobeapi.service;

import com.example.spaceprobeapi.dto.PlanetDto;
import com.example.spaceprobeapi.exception.InvalidValueException;
import com.example.spaceprobeapi.exception.NotFoundValues;
import com.example.spaceprobeapi.model.Planet;
import com.example.spaceprobeapi.repository.IPlanetRepository;
import com.example.spaceprobeapi.service.mapper.PlanetMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PlanetService implements IPlanetService {

    private final IPlanetRepository planetRepository;

    @Autowired
    public PlanetService(IPlanetRepository planetRepository) {
        this.planetRepository = planetRepository;
    }

    @Override
    public Long createPlanet(PlanetDto planet) {
        if (planet.getX() <= 0 || planet.getY() <= 0) {
            throw new InvalidValueException("Área do planeta deve ser maior do que zero.");
        }
        Planet createdPlanet = PlanetMapper.toEntity(planet);
        planetRepository.save(createdPlanet);

        return createdPlanet.getId();
    }

    @Override
    public Planet getPlanetById(Long id) {
        if (id == null) {
            throw new InvalidValueException("Id do planeta não deve ser nulo.");
        }
        Optional<Planet> optionalPlanet = planetRepository.findById(id);
        if (optionalPlanet.isEmpty()) {
            throw new NotFoundValues("Planeta não encontrado.");
        }
        return optionalPlanet.get();
    }
}
