package com.univol.dto.prioridade;

import java.time.LocalDateTime;

public record LogPrioridadeResponseDTO(
        Long id,
        Long pedidoId,
        String prioridadeClassificada,
        String modeloMl,
        LocalDateTime dataClassificacao
) {
}
