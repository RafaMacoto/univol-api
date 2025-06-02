package com.univol.dto.pedido;

import com.univol.model.Prioridade;
import com.univol.model.StatusPedido;

public record PedidoUpdateDTO(
        String descricao,
        Prioridade prioridade,
        StatusPedido status,
        Long organizacaoId
) {
}
