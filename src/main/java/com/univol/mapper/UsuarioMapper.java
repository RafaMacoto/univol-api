package com.univol.mapper;

import com.univol.dto.usuario.UsuarioRequestDTO;
import com.univol.dto.usuario.UsuarioResponseDTO;
import com.univol.dto.usuario.UsuarioUpdateDTO;
import com.univol.model.Usuario;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class UsuarioMapper {

    public static Usuario toEntity(UsuarioRequestDTO dto) {
        return Usuario.builder()
                .nome(dto.nome())
                .email(dto.email())
                .telefone(dto.telefone())
                .role(dto.role())
                .senha(dto.senha())
                .localizacao(dto.localizacao())
                .habilidades(dto.habilidades())
                .build();
    }

    public static UsuarioResponseDTO toDTO(Usuario usuario) {
        return new UsuarioResponseDTO(
                usuario.getId(),
                usuario.getNome(),
                usuario.getEmail(),
                usuario.getTelefone(),
                usuario.getRole().toString(),
                usuario.getLocalizacao(),
                usuario.getHabilidades()
        );
    }

    public static void updateEntity(Usuario u, UsuarioUpdateDTO dto) {
        u.setNome(dto.nome());
        u.setTelefone(dto.telefone());
        u.setLocalizacao(dto.localizacao());
        u.setHabilidades(dto.habilidades() != null ? dto.habilidades() : new ArrayList<>());
    }
}
