package com.univol.dto.prioridade;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

public record LogPrioridadeResponseDTO(
        Long id,
        Long pedidoId,
        String prioridadeClassificada,
        String modeloMl,

        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm")
        LocalDateTime dataClassificacao
) {
}
