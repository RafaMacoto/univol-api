package com.univol.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.univol.dto.pedido.PedidoFilter;
import com.univol.dto.pedido.PedidoRequestDTO;
import com.univol.dto.pedido.PedidoResponseDTO;
import com.univol.dto.pedido.PedidoUpdateDTO;
import com.univol.mapper.PedidoMapper;
import com.univol.model.*;
import com.univol.repository.LogPrioridadeRepository;
import com.univol.repository.OrganizacaoRepository;
import com.univol.repository.PedidoRepository;
import com.univol.specification.PedidoSpecification;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Service
public class PedidoService {

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private OrganizacaoRepository organizacaoRepository;

    @Autowired
    private LogPrioridadeRepository logPrioridadeRepository;

    private final ObjectMapper objectMapper = new ObjectMapper();

    public PedidoResponseDTO create(PedidoRequestDTO dto, Usuario usuario) {
        if (usuario == null) {
            throw new AccessDeniedException("Usuário não autenticado.");
        }

        Organizacao org = organizacaoRepository.findById(dto.organizacaoId())
                .orElseThrow(() -> new EntityNotFoundException("Organização não encontrada."));

        Pedido pedido = PedidoMapper.toEntity(dto, org);

        try {
            String prioridadeStr = classificarPrioridade(dto.descricao());

            Prioridade prioridade = mapearPrioridade(prioridadeStr);
            pedido.setPrioridade(prioridade);

            Pedido salvo = pedidoRepository.save(pedido);

            LogPrioridade log = new LogPrioridade();
            log.setPedido(salvo);
            log.setPrioridadeClassificada(prioridadeStr);
            log.setModeloMl("conectavoluntario-ml-api.onrender.com");
            logPrioridadeRepository.save(log);

            return PedidoMapper.toDTO(salvo);

        } catch (Exception e) {
            throw new RuntimeException("Erro ao classificar prioridade: " + e.getMessage(), e);
        }
    }

    public PedidoResponseDTO update(Long id, PedidoUpdateDTO dto, Usuario usuario) {
        if (usuario == null) {
            throw new AccessDeniedException("Usuário não autenticado.");
        }

        Pedido pedido = pedidoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Pedido não encontrado."));

        Organizacao org = organizacaoRepository.findById(dto.organizacaoId())
                .orElseThrow(() -> new EntityNotFoundException("Organização não encontrada."));

        PedidoMapper.updateEntity(pedido, dto, org);
        Pedido atualizado = pedidoRepository.save(pedido);
        return PedidoMapper.toDTO(atualizado);
    }

    public void delete(Long id, Usuario usuario) {
        if (usuario == null) {
            throw new AccessDeniedException("Usuário não autenticado.");
        }

        Pedido pedido = pedidoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Pedido não encontrado."));

        pedidoRepository.delete(pedido);
    }

    public PedidoResponseDTO getById(Long id, Usuario usuario) {

        if (usuario == null) {
            throw new AccessDeniedException("Usuário não autenticado.");
        }

        Pedido pedido = pedidoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Pedido não encontrado."));
        return PedidoMapper.toDTO(pedido);
    }

    public Page<PedidoResponseDTO> listAll(Pageable pageable, Usuario usuario, PedidoFilter filter) {
        if (usuario == null) {
            throw new AccessDeniedException("Usuário não autenticado.");
        }

        Specification<Pedido> spec = PedidoSpecification.withFilters(filter);
        Page<Pedido> pedidos = pedidoRepository.findAll(spec, pageable);
        return pedidos.map(PedidoMapper::toDTO);
    }

    private String classificarPrioridade(String texto) throws Exception {
        String url = "https://conectavoluntario-ml-api.onrender.com/classificar";
        String json = "{\"texto\": \"" + texto + "\"}";

        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI(url))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(json))
                .build();

        HttpClient client = HttpClient.newHttpClient();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        JsonNode jsonNode = objectMapper.readTree(response.body());
        return jsonNode.get("prioridade").asText();
    }

    private Prioridade mapearPrioridade(String valorApi) {
        try {
            return Prioridade.valueOf(valorApi.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Prioridade inválida recebida da API: " + valorApi);
        }
    }


}
