package com.univol.service;

import com.univol.dto.organizacao.OrganizacaoFilter;
import com.univol.dto.organizacao.OrganizacaoRequestDTO;
import com.univol.dto.organizacao.OrganizacaoResponseDTO;
import com.univol.mapper.OrganizacaoMapper;
import com.univol.model.Organizacao;
import com.univol.model.Usuario;
import com.univol.repository.OrganizacaoRepository;
import com.univol.specification.OrganizacaoSpecification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
public class OrganizacaoService {

    private OrganizacaoRepository organizacaoRepository;

    public Page<OrganizacaoResponseDTO> listAll(Pageable pageable, Usuario usuario, OrganizacaoFilter filter) {
        if (!usuario.getRole().toString().equals("ADMIN")) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Acesso negado");
        }
        var spec = OrganizacaoSpecification.withFilters(filter);

        Page<Organizacao> page = organizacaoRepository.findAll(spec, pageable);

        return page.map(OrganizacaoMapper::toResponseDTO);

    }

    public OrganizacaoResponseDTO findById(Long id, Usuario usuario) {
        Organizacao org = getOrganizacaoById(id);
        if (!usuario.getRole().toString().equals("ADMIN")) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Acesso negado");
        }
        return OrganizacaoMapper.toResponseDTO(org);
    }

    public OrganizacaoResponseDTO create(OrganizacaoRequestDTO dto) {
        Organizacao org = OrganizacaoMapper.toEntity(dto);
        organizacaoRepository.save(org);
        return OrganizacaoMapper.toResponseDTO(org);
    }

    public OrganizacaoResponseDTO update(Long id, OrganizacaoRequestDTO dto, Usuario usuario) {
        Organizacao org = getOrganizacaoById(id);
        if (!usuario.getRole().toString().equals("ADMIN")) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Acesso negado");
        }
        OrganizacaoMapper.updateEntity(org, dto);
        organizacaoRepository.save(org);
        return OrganizacaoMapper.toResponseDTO(org);
    }

    public void delete(Long id, Usuario usuario) {
        Organizacao org = getOrganizacaoById(id);
        if (!usuario.getRole().toString().equals("ADMIN")) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Acesso negado");
        }
        organizacaoRepository.delete(org);
    }

    private Organizacao getOrganizacaoById(Long id) {
        Optional<Organizacao> orgOpt = organizacaoRepository.findById(id);
        if (orgOpt.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Organização não encontrada");
        }
        return orgOpt.get();
    }
}
