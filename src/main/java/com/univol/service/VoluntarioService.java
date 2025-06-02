package com.univol.service;

import com.univol.dto.voluntario.VoluntarioFilter;
import com.univol.dto.voluntario.VoluntarioRequestDTO;
import com.univol.dto.voluntario.VoluntarioResponseDTO;
import com.univol.mapper.VoluntarioMapper;
import com.univol.model.Usuario;
import com.univol.model.Voluntario;
import com.univol.repository.VoluntarioRepository;
import com.univol.specification.VoluntarioSpecification;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
public class VoluntarioService {

    private VoluntarioRepository repository;

    public VoluntarioResponseDTO create(VoluntarioRequestDTO dto, Usuario usuario) {
        if (repository.existsByUsuario(usuario)) {
            throw new IllegalStateException("Usuário já é um voluntário.");
        }

        Voluntario voluntario = VoluntarioMapper.toEntity(dto, usuario);
        return VoluntarioMapper.toDTO(repository.save(voluntario));
    }

    public Page<VoluntarioResponseDTO> findAll(Pageable pageable, VoluntarioFilter filter) {
        Specification<Voluntario> spec = VoluntarioSpecification.withFilters(filter);
        return repository.findAll(spec, pageable).map(VoluntarioMapper::toDTO);
    }

    public VoluntarioResponseDTO findById(Long id, Usuario usuario) {
        Voluntario voluntario = getValidVoluntario(id, usuario);
        return VoluntarioMapper.toDTO(voluntario);
    }

    public VoluntarioResponseDTO update(Long id, VoluntarioRequestDTO dto, Usuario usuario) {
        Voluntario voluntario = getValidVoluntario(id, usuario);
        voluntario.setDisponibilidade(dto.disponibilidade());
        return VoluntarioMapper.toDTO(repository.save(voluntario));
    }

    public void delete(Long id, Usuario usuario) {
        Voluntario voluntario = getValidVoluntario(id, usuario);
        repository.delete(voluntario);
    }

    private Voluntario getValidVoluntario(Long id, Usuario usuario) {
        Voluntario voluntario = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Voluntário não encontrado"));

        boolean isAdmin = usuario.getRole().name().equals("ADMIN");
        boolean isOwner = voluntario.getUsuario().getId().equals(usuario.getId());

        if (!isAdmin && !isOwner) {
            throw new SecurityException("Acesso negado");
        }

        return voluntario;
    }
}
