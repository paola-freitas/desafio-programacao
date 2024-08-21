package com.example.spaceprobeapi.controller;

import com.example.spaceprobeapi.dto.PlanetDto;
import com.example.spaceprobeapi.dto.SpaceProbeDto;
import com.example.spaceprobeapi.service.IPlanetService;
import com.example.spaceprobeapi.service.ISpaceProbeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/space")
public class SpaceController {

    @Autowired
    private ISpaceProbeService spaceProbeService;

    @Autowired
    private IPlanetService planetService;

    @PostMapping("/planet")
    public ResponseEntity<Long> createPlanet(@RequestBody PlanetDto planetDto) {
        Long idPlanetCreated = planetService.createPlanet(planetDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(idPlanetCreated);
    }

    @PostMapping("/space-probe")
    public ResponseEntity<Long> createSpaceProbe(@RequestBody SpaceProbeDto spaceProbeDto) {
        Long idPlanetCreated = spaceProbeService.createSpaceProbe(spaceProbeDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(idPlanetCreated);
    }

    @PutMapping("/space-probe/commands")
    public ResponseEntity<SpaceProbeDto> executeCommands(
            @RequestParam Long id,
            @RequestParam String commands) {
        return ResponseEntity.ok(spaceProbeService.executeCommands(id, commands));
    }
}
