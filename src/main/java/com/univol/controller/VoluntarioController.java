package com.univol.controller;

import com.univol.dto.voluntario.VoluntarioFilter;
import com.univol.dto.voluntario.VoluntarioRequestDTO;
import com.univol.dto.voluntario.VoluntarioResponseDTO;
import com.univol.model.Usuario;
import com.univol.service.VoluntarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("voluntarios")
public class VoluntarioController {

    @Autowired
    private VoluntarioService service;

    @Operation(
            summary = "Criar novo voluntário",
            description = "Cadastra um novo voluntário no sistema.",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Voluntário criado com sucesso"),
                    @ApiResponse(responseCode = "400", description = "Dados inválidos")
            }
    )
    @PostMapping
    @CacheEvict(value = "voluntarios", allEntries = true)
    public ResponseEntity<VoluntarioResponseDTO> create(@RequestBody @Valid VoluntarioRequestDTO dto,
                                                        @AuthenticationPrincipal Usuario usuario) {
        VoluntarioResponseDTO response = service.create(dto, usuario);
        return ResponseEntity.status(201).body(response);
    }

    @Operation(
            summary = "Listar voluntários",
            description = "Retorna uma lista paginada de voluntários.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso")
            }
    )
    @GetMapping
    @Cacheable("voluntarios")
    public ResponseEntity<Page<VoluntarioResponseDTO>> findAll( @PageableDefault(size = 5, sort = "disponibilidade") Pageable pageable, @ModelAttribute VoluntarioFilter filter) {
        Page<VoluntarioResponseDTO> page = service.findAll(pageable, filter);
        return ResponseEntity.ok(page);
    }

    @Operation(
            summary = "Buscar voluntário por ID",
            description = "Retorna os dados do voluntário com o ID informado.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Voluntário encontrado com sucesso"),
                    @ApiResponse(responseCode = "404", description = "Voluntário não encontrado")
            }
    )
    @GetMapping("/{id}")
    public ResponseEntity<VoluntarioResponseDTO> findById(@PathVariable Long id,
                                                          @AuthenticationPrincipal Usuario usuario) {
        VoluntarioResponseDTO response = service.findById(id, usuario);
        return ResponseEntity.ok(response);
    }

    @Operation(
            summary = "Atualizar voluntário",
            description = "Atualiza os dados do voluntário com o ID informado.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Voluntário atualizado com sucesso"),
                    @ApiResponse(responseCode = "400", description = "Dados inválidos"),
                    @ApiResponse(responseCode = "404", description = "Voluntário não encontrado")
            }
    )
    @PutMapping("/{id}")
    @CacheEvict(value = "voluntarios", allEntries = true)
    public ResponseEntity<VoluntarioResponseDTO> update(@PathVariable Long id,
                                                        @RequestBody @Valid VoluntarioRequestDTO dto,
                                                        @AuthenticationPrincipal Usuario usuario) {
        VoluntarioResponseDTO response = service.update(id, dto, usuario);
        return ResponseEntity.ok(response);
    }

    @Operation(
            summary = "Deletar voluntário",
            description = "Exclui o voluntário com o ID informado.",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Voluntário excluído com sucesso"),
                    @ApiResponse(responseCode = "404", description = "Voluntário não encontrado")
            }
    )
    @DeleteMapping("/{id}")
    @CacheEvict(value = "voluntarios", allEntries = true)
    public ResponseEntity<Void> delete(@PathVariable Long id,
                                       @AuthenticationPrincipal Usuario usuario) {
        service.delete(id, usuario);
        return ResponseEntity.noContent().build();
    }
}
