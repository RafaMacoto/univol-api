package com.univol.service;

import com.univol.dto.pedido.PedidoFilter;
import com.univol.dto.pedido.PedidoRequestDTO;
import com.univol.dto.pedido.PedidoResponseDTO;
import com.univol.dto.pedido.PedidoUpdateDTO;
import com.univol.mapper.PedidoMapper;
import com.univol.model.Organizacao;
import com.univol.model.Pedido;
import com.univol.model.RoleUsuario;
import com.univol.model.Usuario;
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

@Service
public class PedidoService {

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private OrganizacaoRepository organizacaoRepository;

    public PedidoResponseDTO create(PedidoRequestDTO dto, Usuario usuario) {
        if (usuario == null) {
            throw new AccessDeniedException("Usuário não autenticado.");
        }

        Organizacao org = organizacaoRepository.findById(dto.organizacaoId())
                .orElseThrow(() -> new EntityNotFoundException("Organização não encontrada."));

        Pedido pedido = PedidoMapper.toEntity(dto, org);
        Pedido salvo = pedidoRepository.save(pedido);
        return PedidoMapper.toDTO(salvo);
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


}
