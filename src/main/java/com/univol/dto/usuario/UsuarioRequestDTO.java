package com.univol.dto.usuario;

import com.univol.model.RoleUsuario;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.List;

public record UsuarioRequestDTO(

        @NotBlank @Size(max = 100) String nome,
        @NotBlank @Email @Size(max = 100) String email,
        @Size(max = 20) String telefone,
        @NotNull RoleUsuario role,
        @NotBlank String senha,
        @Size(max = 150) String localizacao,
        List<String> habilidades
) {
}
