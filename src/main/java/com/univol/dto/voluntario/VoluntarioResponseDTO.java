package com.univol.dto.voluntario;

public record VoluntarioResponseDTO(
        Long id,
        String disponibilidade,
        Long usuarioId
) {
}
