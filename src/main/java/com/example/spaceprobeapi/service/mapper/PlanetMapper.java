package com.example.spaceprobeapi.service.mapper;

import com.example.spaceprobeapi.dto.PlanetDto;
import com.example.spaceprobeapi.exception.NotFoundValues;
import com.example.spaceprobeapi.model.Planet;

public class PlanetMapper {

    public static Planet toEntity(PlanetDto planetDto) {
        if (planetDto == null) {
            throw new NotFoundValues("Informações do planeta não encontradas.");
        }
        Planet entity = new Planet();
        entity.setX(planetDto.getX());
        entity.setY(planetDto.getY());
        return entity;
    }
}
