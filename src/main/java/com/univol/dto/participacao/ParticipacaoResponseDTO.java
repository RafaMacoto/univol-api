package com.univol.dto.participacao;

import java.time.LocalDate;

public record ParticipacaoResponseDTO(
        Long idParticipacao,
        Long pedidoId,
        String descricaoPedido,
        Long voluntarioId,
        String nomeVoluntario,
        LocalDate dataParticipacao
) {
}
