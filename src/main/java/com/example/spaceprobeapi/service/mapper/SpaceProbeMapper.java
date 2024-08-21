package com.example.spaceprobeapi.service.mapper;

import com.example.spaceprobeapi.dto.SpaceProbeDto;
import com.example.spaceprobeapi.enums.Direction;
import com.example.spaceprobeapi.exception.InvalidValueException;
import com.example.spaceprobeapi.exception.NotFoundValues;
import com.example.spaceprobeapi.model.Planet;
import com.example.spaceprobeapi.model.Position;
import com.example.spaceprobeapi.model.SpaceProbe;

public class SpaceProbeMapper {

    public static SpaceProbe toEntity(SpaceProbeDto dto, Planet planet) {
        if (dto == null) {
            throw new NotFoundValues("Informações da sonda não encontradas.");
        }
        validateDirection(dto.getDirection());

        Position position = new Position(dto.getX(), dto.getY());

        SpaceProbe entity = new SpaceProbe();
        entity.setPosition(position);
        entity.setDirection(Direction.valueOf(dto.getDirection().toUpperCase()));
        entity.setPlanet(planet);

        return entity;
    }

    public static SpaceProbe toUpdate(SpaceProbe spaceProbe, String commands) {
        if (spaceProbe == null) {
            throw new NotFoundValues("Sonda não encontrada.");
        }
        spaceProbe.executeCommands(commands);

        return spaceProbe;
    }

    public static SpaceProbeDto toDto(SpaceProbe entity) {
        if (entity == null) {
            throw new NotFoundValues("Sonda não encontrada.");
        }

        SpaceProbeDto dto = new SpaceProbeDto();
        dto.setX(entity.getPosition().getX());
        dto.setY(entity.getPosition().getY());
        dto.setDirection(entity.getDirection().name());
        dto.setPlanetId(entity.getPlanet().getId());

        return dto;
    }

    private static void validateDirection(String directionFromDto) {
        int cont = 0;
        for (Direction d : Direction.values()) {
            if (d.name().equalsIgnoreCase(directionFromDto)) {
                cont++;
            }
        }
        if (cont == 0) {
            throw new InvalidValueException("Direção informada não é válida.");
        }
    }
}
