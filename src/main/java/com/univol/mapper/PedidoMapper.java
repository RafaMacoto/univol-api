package com.univol.mapper;

import com.univol.dto.pedido.PedidoRequestDTO;
import com.univol.dto.pedido.PedidoResponseDTO;
import com.univol.dto.pedido.PedidoUpdateDTO;
import com.univol.model.Organizacao;
import com.univol.model.Pedido;
import org.springframework.stereotype.Component;

@Component
public class PedidoMapper {

    public static Pedido toEntity(PedidoRequestDTO dto, Organizacao organizacao) {
        Pedido pedido = new Pedido();
        pedido.setDescricao(dto.descricao());
        pedido.setStatus(dto.status());
        pedido.setOrganizacao(organizacao);
        return pedido;
    }

    public static PedidoResponseDTO toDTO(Pedido pedido) {
        return new PedidoResponseDTO(
                pedido.getId(),
                pedido.getDescricao(),
                pedido.getPrioridade(),
                pedido.getDataPedido(),
                pedido.getStatus(),
                pedido.getOrganizacao() != null ? pedido.getOrganizacao().getId() : null
        );
    }

    public static void updateEntity(Pedido pedido, PedidoUpdateDTO dto, Organizacao organizacao) {
        pedido.setDescricao(dto.descricao());
        pedido.setPrioridade(dto.prioridade());
        pedido.setStatus(dto.status());
        pedido.setOrganizacao(organizacao);
    }
}
