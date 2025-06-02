package com.univol.dto.participacao;

import jakarta.validation.constraints.NotNull;

public record ParticipacaoRequestDTO(

        @NotNull Long pedidoId,
        @NotNull Long voluntarioId
) {
}
