package com.univol.dto.organizacao;

import com.univol.dto.pedido.PedidoResponseDTO;
import com.univol.model.TipoOrganizacao;

import java.util.List;

public record OrganizacaoResponseDTO(
        Long id,
        String nome,
        TipoOrganizacao tipo,
        String contato,
        List<PedidoResponseDTO> pedidos
) {
}
