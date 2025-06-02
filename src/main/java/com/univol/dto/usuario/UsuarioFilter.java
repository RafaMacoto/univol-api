package com.univol.dto.usuario;

public record UsuarioFilter(
        String nome,
        String email,
        String telefone
) {
}
