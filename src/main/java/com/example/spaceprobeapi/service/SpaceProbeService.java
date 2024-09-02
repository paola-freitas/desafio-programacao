package com.example.spaceprobeapi.service;

import com.example.spaceprobeapi.dto.SpaceProbeDto;
import com.example.spaceprobeapi.exception.InvalidPositionException;
import com.example.spaceprobeapi.exception.InvalidValueException;
import com.example.spaceprobeapi.exception.NotFoundValues;
import com.example.spaceprobeapi.model.Planet;
import com.example.spaceprobeapi.model.Position;
import com.example.spaceprobeapi.model.SpaceProbe;
import com.example.spaceprobeapi.repository.ISpaceProbeRepository;
import com.example.spaceprobeapi.service.mapper.SpaceProbeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Objects;
import java.util.Optional;

@Service
public class SpaceProbeService implements ISpaceProbeService {

    private final ISpaceProbeRepository spaceProbeRepository;
    private final IPlanetService planetService;

    @Autowired
    public SpaceProbeService(ISpaceProbeRepository spaceProbeRepository, IPlanetService planetService) {
        this.spaceProbeRepository = spaceProbeRepository;
        this.planetService = planetService;
    }

    @Override
    public Long createSpaceProbe(SpaceProbeDto spaceProbeDto) {
        if (spaceProbeDto.getX() <= 0 || spaceProbeDto.getY() <= 0) {
            throw new InvalidValueException("Posição x e y devem ser maiores do que zero.");
        }
        if (spaceProbeDto.getDirection() == null) {
            throw new InvalidValueException("Direção não deve ser nula.");
        }
        if (spaceProbeDto.getPlanetId() == null) {
            throw new InvalidValueException("Id do planeta não deve ser nulo.");
        }

        Planet planet = planetService.getPlanetById(spaceProbeDto.getPlanetId());

        SpaceProbe createdSpaceProbe = SpaceProbeMapper.toEntity(spaceProbeDto, planet);
        validadePlanetPosition(createdSpaceProbe);
        spaceProbeRepository.saveAndFlush(createdSpaceProbe);

        return createdSpaceProbe.getId();
    }

    @Override
    public SpaceProbe getSpaceProbeById(Long id) {
        if (id == null) {
            throw new InvalidValueException("Id da sonda não deve ser nulo.");
        }
        Optional<SpaceProbe> optionalSpaceProbe = spaceProbeRepository.findById(id);
        if (optionalSpaceProbe.isEmpty()) {
            throw new NotFoundValues("Sonda não encontrada.");
        }
        return optionalSpaceProbe.get();
    }

    @Transactional
    public SpaceProbeDto executeCommands(Long id, String commands) {
        if (commands.isEmpty()) {
            throw new InvalidValueException("A lista de comandos não deve ser vazia.");
        }

        SpaceProbe spaceProbe = getSpaceProbeById(id);

        SpaceProbe updatedSpaceProbe = SpaceProbeMapper.toUpdate(spaceProbe, commands);
        validadePlanetPosition(updatedSpaceProbe);

        spaceProbeRepository.save(updatedSpaceProbe);

        return SpaceProbeMapper.toDto(updatedSpaceProbe);
    }

    private void validadePlanetPosition(SpaceProbe spaceProbe) {
        checkCoordinates(spaceProbe.getPosition(), spaceProbe.getPlanet());
        checkForCollision(spaceProbe);
    }

    private void checkCoordinates(Position position, Planet planet) {
        if (position.getX() < 0 || position.getY() < 0 || position.getX() >= planet.getX() || position.getY() >= planet.getY()) {
            throw new InvalidValueException("Coordenadas fora dos limites do planeta.");
        }
    }

    private void checkForCollision(SpaceProbe spaceProbe) {
        spaceProbeRepository.findByPositionXAndPositionYAndPlanet(spaceProbe.getPosition().getX(), spaceProbe.getPosition().getY(), spaceProbe.getPlanet())
                .forEach(existingRover -> {
                    if (!spaceProbe.getId().equals(existingRover.getId())) {
                        throw new InvalidPositionException("Colisão detectada na posição (" + spaceProbe.getPosition().getX() + ", " + spaceProbe.getPosition().getY() + ").");
                    }
        });
    }
}
