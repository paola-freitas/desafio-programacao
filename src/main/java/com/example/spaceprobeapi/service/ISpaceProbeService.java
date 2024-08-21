package com.example.spaceprobeapi.service;

import com.example.spaceprobeapi.dto.SpaceProbeDto;
import com.example.spaceprobeapi.model.SpaceProbe;

public interface ISpaceProbeService {

    Long createSpaceProbe(SpaceProbeDto spaceProbeDto);

    SpaceProbe getSpaceProbeById(Long id);

    SpaceProbeDto executeCommands(Long id, String commands);
}
