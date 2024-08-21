package com.example.spaceprobeapi.service;

import com.example.spaceprobeapi.dto.PlanetDto;
import com.example.spaceprobeapi.exception.InvalidValueException;
import com.example.spaceprobeapi.exception.NotFoundValues;
import com.example.spaceprobeapi.model.Planet;
import com.example.spaceprobeapi.repository.IPlanetRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PlanetServiceTest {

    @Mock
    private IPlanetRepository planetRepository;

    @InjectMocks
    private PlanetService planetService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreatePlanetWithValidData() {
        PlanetDto planetDto = new PlanetDto(5, 5);

        when(planetRepository.save(any(Planet.class))).thenAnswer(invocation -> {
            Planet savedPlanet = invocation.getArgument(0);
            savedPlanet.setId(1L);
            return savedPlanet;
        });

        Long id = planetService.createPlanet(planetDto);

        assertNotNull(id);
        verify(planetRepository, times(1)).save(any(Planet.class));
    }

    @Test
    void testCreatePlanetWithInvalidSize() {
        PlanetDto planetDto = new PlanetDto(-1, 5);

        InvalidValueException exception = assertThrows(InvalidValueException.class,
                () -> planetService.createPlanet(planetDto));

        assertEquals("Área do planeta deve ser maior do que zero.", exception.getMessage());
    }

    @Test
    void testGetPlanetByIdNotFound() {
        when(planetRepository.findById(1L)).thenReturn(Optional.empty());

        NotFoundValues exception = assertThrows(NotFoundValues.class,
                () -> planetService.getPlanetById(1L));

        assertEquals("Planeta não encontrado.", exception.getMessage());
    }

    @Test
    void testGetPlanetByIdWithValidData() {
        Planet planet = new Planet(1L, 5, 5);

        when(planetRepository.findById(1L)).thenReturn(Optional.of(planet));

        Planet foundPlanet = planetService.getPlanetById(1L);

        assertEquals(1L, foundPlanet.getId());
        verify(planetRepository, times(1)).findById(1L);
    }
}
