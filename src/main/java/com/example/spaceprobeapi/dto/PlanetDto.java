package com.example.spaceprobeapi.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PlanetDto {
    @NotBlank(message = "X é obrigatório.")
    private int x;

    @NotBlank(message = "Y é obrigatório.")
    private int y;
}
