package com.univol.controller;

import com.univol.dto.organizacao.OrganizacaoFilter;
import com.univol.dto.organizacao.OrganizacaoRequestDTO;
import com.univol.dto.organizacao.OrganizacaoResponseDTO;
import com.univol.model.Usuario;
import com.univol.service.OrganizacaoService;
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
@RequestMapping("organizacoes")
public class OrganizacaoController {

    @Autowired
    private OrganizacaoService organizacaoService;

    @Operation(
            summary = "Listar organizações",
            description = "Retorna uma lista paginada de organizações associadas ao usuário logado.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Organizações listadas com sucesso")
            }
    )
    @GetMapping
    @Cacheable("organizacoes")
    public ResponseEntity<Page<OrganizacaoResponseDTO>> listAll(
            @ModelAttribute OrganizacaoFilter filter,
            @AuthenticationPrincipal Usuario usuario,
            @PageableDefault(size = 5, sort = "nome") Pageable pageable
    ) {
        Page<OrganizacaoResponseDTO> page = organizacaoService.listAll(pageable, usuario, filter);
        return ResponseEntity.ok(page);
    }

    @Operation(
            summary = "Buscar organização por ID",
            description = "Retorna os dados de uma organização específica do usuário logado.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Organização encontrada"),
                    @ApiResponse(responseCode = "404", description = "Organização não encontrada")
            }
    )
    @GetMapping("/{id}")
    public ResponseEntity<OrganizacaoResponseDTO> findById(
            @PathVariable Long id,
            @AuthenticationPrincipal Usuario usuario
    ) {
        OrganizacaoResponseDTO dto = organizacaoService.findById(id, usuario);
        return ResponseEntity.ok(dto);
    }

    @Operation(
            summary = "Criar nova organização",
            description = "Cadastra uma nova organização.",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Organização criada com sucesso"),
                    @ApiResponse(responseCode = "400", description = "Dados inválidos")
            }
    )
    @PostMapping
    @CacheEvict(value = "organizacoes", allEntries = true)
    public ResponseEntity<OrganizacaoResponseDTO> create(@RequestBody @Valid OrganizacaoRequestDTO dto) {
        OrganizacaoResponseDTO created = organizacaoService.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @Operation(
            summary = "Atualizar organização",
            description = "Atualiza os dados de uma organização existente.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Organização atualizada com sucesso"),
                    @ApiResponse(responseCode = "404", description = "Organização não encontrada")
            }
    )
    @PutMapping("/{id}")
    @CacheEvict(value = "organizacoes", allEntries = true)
    public ResponseEntity<OrganizacaoResponseDTO> update(
            @PathVariable Long id,
            @RequestBody @Valid OrganizacaoRequestDTO dto,
            @AuthenticationPrincipal Usuario usuario
    ) {
        OrganizacaoResponseDTO updated = organizacaoService.update(id, dto, usuario);
        return ResponseEntity.ok(updated);
    }

    @Operation(
            summary = "Deletar organização",
            description = "Remove uma organização associada ao usuário logado.",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Organização deletada com sucesso"),
                    @ApiResponse(responseCode = "404", description = "Organização não encontrada")
            }
    )
    @DeleteMapping("/{id}")
    @CacheEvict(value = "organizacoes", allEntries = true)
    public ResponseEntity<Void> delete(
            @PathVariable Long id,
            @AuthenticationPrincipal Usuario usuario
    ) {
        organizacaoService.delete(id, usuario);
        return ResponseEntity.noContent().build();
    }
}
