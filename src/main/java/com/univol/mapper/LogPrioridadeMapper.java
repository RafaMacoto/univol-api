package com.univol.mapper;

import com.univol.dto.prioridade.LogPrioridadeRequestDTO;
import com.univol.dto.prioridade.LogPrioridadeResponseDTO;
import com.univol.model.LogPrioridade;
import com.univol.model.Pedido;
import org.springframework.stereotype.Component;

@Component
public class LogPrioridadeMapper {

    public static LogPrioridadeResponseDTO toResponseDTO(LogPrioridade log) {
        return new LogPrioridadeResponseDTO(
                log.getId(),
                log.getPedido().getId(),
                log.getPrioridadeClassificada(),
                log.getModeloMl(),
                log.getDataClassificacao()
        );
    }

    public static LogPrioridade toEntity(LogPrioridadeRequestDTO dto, Pedido pedido) {
        LogPrioridade log = new LogPrioridade();
        log.setPedido(pedido);
        log.setPrioridadeClassificada(dto.prioridadeClassificada());
        log.setModeloMl(dto.modeloMl());
        return log;
    }
}
