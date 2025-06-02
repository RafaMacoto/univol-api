package com.univol.dto.prioridade;

public record LogPrioridadeRequestDTO(
        Long pedidoId,
        String prioridadeClassificada,
        String modeloMl
) {
}
