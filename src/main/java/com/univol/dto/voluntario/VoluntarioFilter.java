package com.univol.dto.voluntario;

public record VoluntarioFilter(
        Long id,
        Long idUsuario,
        String disponibilidade
) {
}
