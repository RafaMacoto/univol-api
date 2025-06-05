package com.univol.service;

import com.univol.dto.prioridade.LogPrioridadeFilter;
import com.univol.dto.prioridade.LogPrioridadeRequestDTO;
import com.univol.dto.prioridade.LogPrioridadeResponseDTO;
import com.univol.mapper.LogPrioridadeMapper;
import com.univol.model.LogPrioridade;
import com.univol.model.Pedido;
import com.univol.model.Usuario;
import com.univol.repository.LogPrioridadeRepository;
import com.univol.repository.PedidoRepository;
import com.univol.specification.LogPrioridadeSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class LogPrioridadeService {

    @Autowired
    private LogPrioridadeRepository repository;

    @Autowired
    private PedidoRepository pedidoRepository;

    public Page<LogPrioridadeResponseDTO> listAll(Pageable pageable, LogPrioridadeFilter filter) {

        var spec = LogPrioridadeSpecification.withFilters(filter);
        Page<LogPrioridade> page = repository.findAll(spec, pageable);

        return page.map(LogPrioridadeMapper::toResponseDTO);
    }

    public LogPrioridadeResponseDTO findById(Long id) {
        LogPrioridade log = getById(id);
        return LogPrioridadeMapper.toResponseDTO(log);
    }

    private LogPrioridade getById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Log não encontrado"));
    }
}
