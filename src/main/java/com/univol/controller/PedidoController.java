package com.univol.controller;

import com.univol.dto.pedido.PedidoFilter;
import com.univol.dto.pedido.PedidoRequestDTO;
import com.univol.dto.pedido.PedidoResponseDTO;
import com.univol.dto.pedido.PedidoUpdateDTO;
import com.univol.model.Usuario;
import com.univol.service.PedidoService;
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
@RequestMapping("pedidos")
public class PedidoController {

    @Autowired
    private PedidoService pedidoService;

    @Operation(
            summary = "Listar pedidos",
            description = "Retorna uma lista paginada de pedidos do usuário logado.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Pedidos listados com sucesso")
            }
    )
    @GetMapping
    @Cacheable("pedidos")
    public ResponseEntity<Page<PedidoResponseDTO>> listarPedidos(
            @ModelAttribute PedidoFilter filter,
            @PageableDefault(size = 10) Pageable pageable,
            @AuthenticationPrincipal Usuario usuario
    ) {
        Page<PedidoResponseDTO> page = pedidoService.listAll(pageable, usuario, filter);
        return ResponseEntity.ok(page);
    }

    @Operation(
            summary = "Buscar pedido por ID",
            description = "Retorna os dados do pedido com o ID informado.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Pedido encontrado com sucesso"),
                    @ApiResponse(responseCode = "404", description = "Pedido não encontrado")
            }
    )
    @GetMapping("/{id}")
    public ResponseEntity<PedidoResponseDTO> buscarPorId(
            @PathVariable Long id,
            @AuthenticationPrincipal Usuario usuario
    ) {
        PedidoResponseDTO dto = pedidoService.getById(id, usuario);
        return ResponseEntity.ok(dto);
    }

    @Operation(
            summary = "Criar novo pedido",
            description = "Cadastra um novo pedido no sistema.",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Pedido criado com sucesso"),
                    @ApiResponse(responseCode = "400", description = "Dados inválidos")
            }
    )
    @PostMapping
    @CacheEvict(value = "pedidos", allEntries = true)
    public ResponseEntity<PedidoResponseDTO> criar(
            @RequestBody @Valid PedidoRequestDTO dto,
            @AuthenticationPrincipal Usuario usuario
    ) {
        PedidoResponseDTO criado = pedidoService.create(dto, usuario);
        return ResponseEntity.status(HttpStatus.CREATED).body(criado);
    }

    @Operation(
            summary = "Atualizar pedido",
            description = "Atualiza os dados do pedido com o ID informado.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Pedido atualizado com sucesso"),
                    @ApiResponse(responseCode = "400", description = "Dados inválidos"),
                    @ApiResponse(responseCode = "404", description = "Pedido não encontrado")
            }
    )
    @PutMapping("/{id}")
    @CacheEvict(value = "pedidos", allEntries = true)
    public ResponseEntity<PedidoResponseDTO> atualizar(
            @PathVariable Long id,
            @RequestBody @Valid PedidoUpdateDTO dto,
            @AuthenticationPrincipal Usuario usuario
    ) {
        PedidoResponseDTO atualizado = pedidoService.update(id, dto, usuario);
        return ResponseEntity.ok(atualizado);
    }

    @Operation(
            summary = "Deletar pedido",
            description = "Exclui o pedido com o ID informado.",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Pedido excluído com sucesso"),
                    @ApiResponse(responseCode = "404", description = "Pedido não encontrado")
            }
    )
    @DeleteMapping("/{id}")
    @CacheEvict(value = "pedidos", allEntries = true)
    public ResponseEntity<Void> deletar(
            @PathVariable Long id,
            @AuthenticationPrincipal Usuario usuario
    ) {
        pedidoService.delete(id, usuario);
        return ResponseEntity.noContent().build();
    }
}
