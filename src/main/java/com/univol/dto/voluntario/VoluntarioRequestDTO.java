package com.univol.dto.voluntario;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record VoluntarioRequestDTO(
        @NotBlank
        @Size(max = 100)
        String disponibilidade
) {
}
