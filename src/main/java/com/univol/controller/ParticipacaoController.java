package com.univol.controller;


import com.univol.dto.participacao.ParticipacaoFilter;
import com.univol.dto.participacao.ParticipacaoRequestDTO;
import com.univol.dto.participacao.ParticipacaoResponseDTO;
import com.univol.model.Usuario;
import com.univol.service.ParticipacaoService;
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
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("participacoes")
public class ParticipacaoController {

    @Autowired
    private ParticipacaoService participacaoService;

    @Operation(
            summary = "Listar participações",
            description = "Retorna uma lista paginada de todas as participações.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Participações listadas com sucesso")
            }
    )
    @GetMapping
    @Cacheable("participacoes")
    public ResponseEntity<Page<ParticipacaoResponseDTO>> listAll(@PageableDefault(size = 5, sort = "dataParticipacao") Pageable pageable,
                                                                 @AuthenticationPrincipal Usuario usuario,
                                                                 @ModelAttribute ParticipacaoFilter filter) {
        Page<ParticipacaoResponseDTO> page = participacaoService.listAll(pageable, usuario, filter);
        return ResponseEntity.ok(page);
    }

    @Operation(
            summary = "Buscar participação por ID",
            description = "Retorna os dados da participação com o ID informado.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Participação encontrada com sucesso"),
                    @ApiResponse(responseCode = "404", description = "Participação não encontrada")
            }
    )
    @GetMapping("/{id}")
    public ResponseEntity<ParticipacaoResponseDTO> findById(@PathVariable Long id) {
        ParticipacaoResponseDTO dto = participacaoService.findById(id);
        return ResponseEntity.ok(dto);
    }

    @Operation(
            summary = "Criar nova participação",
            description = "Cadastra uma nova participação vinculada ao usuário logado.",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Participação criada com sucesso"),
                    @ApiResponse(responseCode = "400", description = "Dados inválidos")
            }
    )
    @PostMapping
    @CacheEvict(value = "participacoes", allEntries = true)
    public ResponseEntity<ParticipacaoResponseDTO> create(
            @RequestBody @Valid ParticipacaoRequestDTO dto,
            @AuthenticationPrincipal Usuario usuarioAutenticado
    ) {
        ParticipacaoResponseDTO created = participacaoService.create(dto, usuarioAutenticado);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @Operation(
            summary = "Atualizar participação",
            description = "Atualiza os dados da participação com o ID informado.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Participação atualizada com sucesso"),
                    @ApiResponse(responseCode = "400", description = "Dados inválidos"),
                    @ApiResponse(responseCode = "404", description = "Participação não encontrada")
            }
    )
    @PutMapping("/{id}")
    @CacheEvict(value = "participacoes", allEntries = true)
    public ResponseEntity<ParticipacaoResponseDTO> update(
            @PathVariable Long id,
            @RequestBody @Valid ParticipacaoRequestDTO dto,
            @AuthenticationPrincipal Usuario usuarioAutenticado
    ) {
        ParticipacaoResponseDTO updated = participacaoService.update(id, dto, usuarioAutenticado);
        return ResponseEntity.ok(updated);
    }

    @Operation(
            summary = "Deletar participação",
            description = "Exclui a participação com o ID informado.",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Participação excluída com sucesso"),
                    @ApiResponse(responseCode = "404", description = "Participação não encontrada")
            }
    )
    @DeleteMapping("/{id}")
    @CacheEvict(value = "participacoes", allEntries = true)
    public ResponseEntity<Void> delete(
            @PathVariable Long id,
            @AuthenticationPrincipal Usuario usuarioAutenticado
    ) {
        participacaoService.delete(id, usuarioAutenticado);
        return ResponseEntity.noContent().build();
    }
}
