package com.univol.dto.usuario;

import java.util.List;

public record UsuarioUpdateDTO(String nome, String telefone, String localizacao, List<String> habilidades) {
}
