package com.univol.mapper;

import com.univol.dto.participacao.ParticipacaoRequestDTO;
import com.univol.dto.participacao.ParticipacaoResponseDTO;
import com.univol.model.Participacao;
import com.univol.model.Pedido;
import com.univol.model.Voluntario;
import org.springframework.stereotype.Component;

@Component
public class ParticipacaoMapper {

    public static ParticipacaoResponseDTO toResponseDTO(Participacao p) {
        return new ParticipacaoResponseDTO(
                p.getIdParticipacao(),
                p.getPedido().getId(),
                p.getPedido().getDescricao(),
                p.getVoluntario().getId(),
                p.getVoluntario().getUsuario().getNome(),
                p.getDataParticipacao()
        );
    }

    public static Participacao toEntity(ParticipacaoRequestDTO dto, Pedido pedido, Voluntario voluntario) {
        Participacao p = new Participacao();
        p.setPedido(pedido);
        p.setVoluntario(voluntario);
        return p;
    }
}
