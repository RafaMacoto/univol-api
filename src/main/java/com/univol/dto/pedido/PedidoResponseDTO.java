package com.univol.dto.pedido;

import com.univol.model.Prioridade;
import com.univol.model.StatusPedido;

import java.time.LocalDate;

public record PedidoResponseDTO(
        Long id,
        String descricao,
        Prioridade prioridade,
        LocalDate dataPedido,
        StatusPedido status,
        Long organizacaoId
) {
}
