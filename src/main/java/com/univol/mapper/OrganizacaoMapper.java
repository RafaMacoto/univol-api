package com.univol.mapper;

import com.univol.dto.organizacao.OrganizacaoRequestDTO;
import com.univol.dto.organizacao.OrganizacaoResponseDTO;
import com.univol.dto.pedido.PedidoResponseDTO;
import com.univol.model.Organizacao;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class OrganizacaoMapper {

    public static OrganizacaoResponseDTO toResponseDTO(Organizacao org) {
        return new OrganizacaoResponseDTO(
                org.getId(),
                org.getNome(),
                org.getTipo(),
                org.getContato(),
                org.getPedidos().stream()
                        .map(p -> new PedidoResponseDTO(
                                p.getId(),
                                p.getDescricao(),
                                p.getPrioridade(),
                                p.getDataPedido(),
                                p.getStatus(),
                                p.getOrganizacao().getId()
                        ))
                        .collect(Collectors.toList())
        );
    }

    public static Organizacao toEntity(OrganizacaoRequestDTO dto) {
        Organizacao org = new Organizacao();
        org.setNome(dto.nome());
        org.setTipo(dto.tipo());
        org.setContato(dto.contato());
        return org;
    }

    public static void updateEntity(Organizacao org, OrganizacaoRequestDTO dto) {
        org.setNome(dto.nome());
        org.setTipo(dto.tipo());
        org.setContato(dto.contato());
    }
}
