package com.example.spaceprobeapi.service;

import com.example.spaceprobeapi.dto.SpaceProbeDto;
import com.example.spaceprobeapi.enums.Direction;
import com.example.spaceprobeapi.exception.InvalidValueException;
import com.example.spaceprobeapi.exception.NotFoundValues;
import com.example.spaceprobeapi.model.Planet;
import com.example.spaceprobeapi.model.Position;
import com.example.spaceprobeapi.model.SpaceProbe;
import com.example.spaceprobeapi.repository.ISpaceProbeRepository;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class SpaceProbeServiceTest {

    @Mock
    private ISpaceProbeRepository spaceProbeRepository;

    @Mock
    private IPlanetService planetService;

    @InjectMocks
    private SpaceProbeService spaceProbeService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Contract(value = " -> new")
    private static @NotNull SpaceProbeDto validSpaceProbeDto() {
        return new SpaceProbeDto(1, 2, Direction.NORTH.name(), 1L);
    }

    @Contract(value = " -> new")
    private static @NotNull Planet validPlanet() {
        return new Planet(1L, 5, 5);
    }

    private static @NotNull SpaceProbe validSpaceProbe() {
        Planet planet = validPlanet();
        Position position = new Position(1, 3);
        return new SpaceProbe(1L, position, Direction.NORTH, planet);
    }

    @Test
    void testCreateSpaceProbeSuccess() {
        SpaceProbeDto spaceProbeDto = validSpaceProbeDto();
        Planet planet = validPlanet();

        when(planetService.getPlanetById(1L)).thenReturn(planet);
        when(spaceProbeRepository.saveAndFlush(any(SpaceProbe.class))).thenAnswer(invocation -> {
            SpaceProbe savedSpaceProbe = invocation.getArgument(0);
            savedSpaceProbe.setId(1L);
            return savedSpaceProbe;
        });
        Long id = spaceProbeService.createSpaceProbe(spaceProbeDto);

        assertNotNull(id);
        verify(spaceProbeRepository, times(1)).saveAndFlush(any(SpaceProbe.class));
    }

    @Test
    void testCreateSpaceProbeWithInvalidPosition() {
        SpaceProbeDto spaceProbeDto = validSpaceProbeDto();
        spaceProbeDto.setY(-1);

        InvalidValueException exception = assertThrows(InvalidValueException.class,
                () -> spaceProbeService.createSpaceProbe(spaceProbeDto));

        assertEquals("Posição x e y devem ser maiores do que zero.", exception.getMessage());
    }

    @Test
    void testCreateSpaceProbeWithNullDirection() {
        SpaceProbeDto spaceProbeDto = validSpaceProbeDto();
        spaceProbeDto.setDirection(null);

        InvalidValueException exception = assertThrows(InvalidValueException.class,
                () -> spaceProbeService.createSpaceProbe(spaceProbeDto));

        assertEquals("Direção não deve ser nula.", exception.getMessage());
    }

    @Test
    void testCreateSpaceProbeWithInvalidDirection() {
        SpaceProbeDto spaceProbeDto = validSpaceProbeDto();
        spaceProbeDto.setDirection("NORT");
        Planet planet = validPlanet();

        when(planetService.getPlanetById(1L)).thenReturn(planet);
        InvalidValueException exception = assertThrows(InvalidValueException.class,
                () -> spaceProbeService.createSpaceProbe(spaceProbeDto));

        assertEquals("Direção informada não é válida.", exception.getMessage());
    }

    @Test
    void testCreateSpaceProbeWithNullPlanetId() {
        SpaceProbeDto spaceProbeDto = validSpaceProbeDto();
        spaceProbeDto.setPlanetId(null);

        InvalidValueException exception = assertThrows(InvalidValueException.class,
                () -> spaceProbeService.createSpaceProbe(spaceProbeDto));

        assertEquals("Id do planeta não deve ser nulo.", exception.getMessage());
    }

    @Test
    void testGetSpaceProbeSuccess() {
        SpaceProbe spaceProbe = validSpaceProbe();

        when(spaceProbeRepository.findById(1L)).thenReturn(Optional.of(spaceProbe));
        SpaceProbe foundSpaceProbe = spaceProbeService.getSpaceProbeById(1L);

        assertNotNull(foundSpaceProbe);
        assertEquals(1L, foundSpaceProbe.getId());
        verify(spaceProbeRepository, times(1)).findById(1L);
    }


    @Test
    void testGetSpaceProbeByIdNullId() {
        InvalidValueException exception = assertThrows(InvalidValueException.class,
                () -> spaceProbeService.getSpaceProbeById(null));

        assertEquals("Id da sonda não deve ser nulo.", exception.getMessage());
    }

    @Test
    void testGetSpaceProbeByNotFound() {
        when(spaceProbeRepository.findById(1L)).thenReturn(Optional.empty());
        NotFoundValues exception = assertThrows(NotFoundValues.class,
                () -> spaceProbeService.getSpaceProbeById(1L));

        assertEquals("Sonda não encontrada.", exception.getMessage());
    }


    @Test
    void testExecuteCommandsWithValidData() {
        SpaceProbe spaceProbe = validSpaceProbe();
        SpaceProbeDto spaceProbeDto = validSpaceProbeDto();
        String commands = "LMLMLMLMM";

        when(spaceProbeRepository.findById(1L)).thenReturn(Optional.of(spaceProbe));
        when(spaceProbeRepository.save(any(SpaceProbe.class))).thenReturn(spaceProbe);

        SpaceProbeDto result = spaceProbeService.executeCommands(1L, commands);

        assertEquals(spaceProbeDto.getDirection(), result.getDirection());
        verify(spaceProbeRepository, times(1)).save(any(SpaceProbe.class));
    }

    @Test
    void testExecuteCommandsWithEmptyCommands() {
        InvalidValueException exception = assertThrows(InvalidValueException.class,
                () -> spaceProbeService.executeCommands(1L, ""));

        assertEquals("A lista de comandos não deve ser vazia.", exception.getMessage());
    }
}
