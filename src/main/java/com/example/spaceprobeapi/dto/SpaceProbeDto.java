package com.example.spaceprobeapi.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SpaceProbeDto {

    @NotBlank(message = "X é obrigatório.")
    private int x;

    @NotBlank(message = "Y é obrigatório.")
    private int y;

    @NotNull(message = "Direção da sonda é obrigatória.")
    private String direction;

    @NotBlank(message = "Id do planeta é obrigatório.")
    private Long planetId;

}
