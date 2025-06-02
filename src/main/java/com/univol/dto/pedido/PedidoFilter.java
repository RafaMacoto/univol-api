package com.univol.dto.pedido;

import com.univol.model.Prioridade;
import com.univol.model.StatusPedido;

import java.time.LocalDate;

public record PedidoFilter(

        Long id,
        String descricao,
        Prioridade prioridade,
        LocalDate dataPedidoInicio,
        LocalDate dataPedidoFim,
        StatusPedido status,
        Long idOrganizacao
) {
}
