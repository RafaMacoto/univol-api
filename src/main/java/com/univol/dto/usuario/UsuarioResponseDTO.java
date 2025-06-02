package com.univol.dto.usuario;

import java.util.List;

public record UsuarioResponseDTO(
        Long id,
        String nome,
        String email,
        String telefone,
        String role,
        String localizacao,
        List<String> habilidades
) {
}
