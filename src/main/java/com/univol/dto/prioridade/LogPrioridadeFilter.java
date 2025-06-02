package com.univol.dto.prioridade;

public record LogPrioridadeFilter(
        Long idPedido,
        String prioridadeClassificada,
        String modeloMl
) {
}
