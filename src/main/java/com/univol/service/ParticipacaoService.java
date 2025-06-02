package com.univol.service;


import com.univol.dto.participacao.ParticipacaoFilter;
import com.univol.dto.participacao.ParticipacaoRequestDTO;
import com.univol.dto.participacao.ParticipacaoResponseDTO;
import com.univol.mapper.ParticipacaoMapper;
import com.univol.model.Participacao;
import com.univol.model.Pedido;
import com.univol.model.Usuario;
import com.univol.model.Voluntario;
import com.univol.repository.ParticipacaoRepository;
import com.univol.repository.PedidoRepository;
import com.univol.repository.VoluntarioRepository;
import com.univol.specification.ParticipacaoSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
public class ParticipacaoService {

    @Autowired
    private ParticipacaoRepository participacaoRepository;

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private VoluntarioRepository voluntarioRepository;

    public Page<ParticipacaoResponseDTO> listAll(Pageable pageable, Usuario usuario, ParticipacaoFilter filter) {
        if (usuario == null) {
            throw new SecurityException("Usuário não autenticado.");
        }

        var spec = ParticipacaoSpecification.withFilters(filter);

        Page<Participacao> page = participacaoRepository.findAll(spec, pageable);

        return page.map(ParticipacaoMapper::toResponseDTO);
    }

    public ParticipacaoResponseDTO findById(Long id) {
        Participacao p = getParticipacaoById(id);
        return ParticipacaoMapper.toResponseDTO(p);
    }

    public ParticipacaoResponseDTO create(ParticipacaoRequestDTO dto, Usuario usuarioAutenticado) {
        Pedido pedido = pedidoRepository.findById(dto.pedidoId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Pedido não encontrado"));
        Voluntario voluntario = voluntarioRepository.findById(dto.voluntarioId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Voluntário não encontrado"));

        if (!voluntario.getUsuario().getId().equals(usuarioAutenticado.getId())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Você não pode criar participação para outro voluntário");
        }

        Optional<Participacao> existing = participacaoRepository.findByPedidoAndVoluntario(pedido, voluntario);
        if (existing.isPresent()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Participação já cadastrada para este pedido e voluntário");
        }

        Participacao participacao = ParticipacaoMapper.toEntity(dto, pedido, voluntario);
        participacaoRepository.save(participacao);
        return ParticipacaoMapper.toResponseDTO(participacao);
    }

    public ParticipacaoResponseDTO update(Long id, ParticipacaoRequestDTO dto, Usuario usuarioAutenticado) {
        Participacao participacao = getParticipacaoById(id);

        if (!participacao.getVoluntario().getUsuario().getId().equals(usuarioAutenticado.getId())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Você não pode atualizar esta participação");
        }

        Pedido pedido = pedidoRepository.findById(dto.pedidoId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Pedido não encontrado"));
        Voluntario voluntario = voluntarioRepository.findById(dto.voluntarioId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Voluntário não encontrado"));

        Optional<Participacao> existing = participacaoRepository.findByPedidoAndVoluntario(pedido, voluntario);
        if (existing.isPresent() && !existing.get().getIdParticipacao().equals(id)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Participação duplicada");
        }

        participacao.setPedido(pedido);
        participacao.setVoluntario(voluntario);
        participacaoRepository.save(participacao);

        return ParticipacaoMapper.toResponseDTO(participacao);
    }

    public void delete(Long id, Usuario usuarioAutenticado) {
        Participacao p = getParticipacaoById(id);

        if (!p.getVoluntario().getUsuario().getId().equals(usuarioAutenticado.getId())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Você não pode deletar esta participação");
        }

        participacaoRepository.delete(p);
    }

    private Participacao getParticipacaoById(Long id) {
        return participacaoRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Participação não encontrada"));
    }


}
