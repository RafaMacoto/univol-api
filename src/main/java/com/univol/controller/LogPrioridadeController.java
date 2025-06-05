package com.univol.controller;


import com.univol.dto.prioridade.LogPrioridadeFilter;
import com.univol.dto.prioridade.LogPrioridadeRequestDTO;
import com.univol.dto.prioridade.LogPrioridadeResponseDTO;
import com.univol.service.LogPrioridadeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("logs-prioridade")
public class LogPrioridadeController {

    @Autowired
    private LogPrioridadeService service;

    @Operation(
            summary = "Listar logs de prioridade",
            description = "Retorna uma lista paginada de logs de alteração de prioridade.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Logs listados com sucesso")
            }
    )
    @GetMapping
    @Cacheable("logsPrioridade")
    public ResponseEntity<Page<LogPrioridadeResponseDTO>> listAll(@PageableDefault(size = 10) Pageable pageable, @ModelAttribute LogPrioridadeFilter filter) {
        return ResponseEntity.ok(service.listAll(pageable, filter));
    }

    @Operation(
            summary = "Buscar log de prioridade por ID",
            description = "Retorna os dados de um log de prioridade específico.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Log encontrado"),
                    @ApiResponse(responseCode = "404", description = "Log não encontrado")
            }
    )
    @GetMapping("/{id}")
    public ResponseEntity<LogPrioridadeResponseDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }


}
