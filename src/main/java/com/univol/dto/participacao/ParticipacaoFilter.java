package com.univol.dto.participacao;

import java.time.LocalDate;

public record ParticipacaoFilter(
        Long pedidoId,
        Long voluntarioId,
        LocalDate dataParticipacaoInicio,
        LocalDate dataParticipacaoFim
) {
}
