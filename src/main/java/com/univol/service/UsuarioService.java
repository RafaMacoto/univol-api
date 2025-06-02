package com.univol.service;

import com.univol.dto.usuario.UsuarioFilter;
import com.univol.dto.usuario.UsuarioRequestDTO;
import com.univol.dto.usuario.UsuarioResponseDTO;
import com.univol.dto.usuario.UsuarioUpdateDTO;
import com.univol.mapper.UsuarioMapper;
import com.univol.model.RoleUsuario;
import com.univol.model.Usuario;
import com.univol.repository.UsuarioRepository;
import com.univol.specification.UsuarioSpecification;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository repository;

    @Autowired
    private PasswordEncoder passwordEncoder;


    public UsuarioResponseDTO createUsuario(UsuarioRequestDTO dto) {

        if (repository.existsByEmail(dto.email())) {
            throw new RuntimeException("Email já cadastrado");
        }

        Usuario u = UsuarioMapper.toEntity(dto);
        u.setSenha(passwordEncoder.encode(dto.senha()));;
        Usuario salvo = repository.save(u);

        return UsuarioMapper.toDTO(salvo);
    }

    public Page<UsuarioResponseDTO> listarUsuarios(Usuario logado, Pageable pageable, UsuarioFilter filter) {
        if (!isAdmin(logado)) {
            throw new AccessDeniedException("Somente admin pode listar todos usuários");
        }
        Specification<Usuario> spec = UsuarioSpecification.withFilters(filter);

        return repository.findAll(spec, pageable)
                .map(UsuarioMapper::toDTO);
    }

    public UsuarioResponseDTO buscarPorId(Long id, Usuario logado) {
        Usuario u = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado"));
        if (!logado.getId().equals(id) && !isAdmin(logado)) {
            throw new AccessDeniedException("Você só pode acessar seu próprio usuário");
        }
        return UsuarioMapper.toDTO(u);
    }

    public UsuarioResponseDTO atualizarUsuario(Long id, UsuarioUpdateDTO dto, Usuario logado) {
        Usuario u = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado"));

        if (!logado.getId().equals(id) && !isAdmin(logado)) {
            throw new AccessDeniedException("Você só pode atualizar seu próprio usuário");
        }

        UsuarioMapper.updateEntity(u, dto);
        Usuario salvo = repository.save(u);

        return UsuarioMapper.toDTO(salvo);
    }

    public void deletarUsuario(Long id, Usuario logado) {
        if (!repository.existsById(id)) {
            throw new EntityNotFoundException("Usuário não encontrado");
        }
        if (!logado.getId().equals(id) && !isAdmin(logado)) {
            throw new AccessDeniedException("Você só pode deletar seu próprio usuário");
        }
        repository.deleteById(id);
    }

    private boolean isAdmin(Usuario usuario) {
        return usuario.getRole() == RoleUsuario.ADMIN;
    }
}
