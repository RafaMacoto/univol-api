package com.univol.dto.organizacao;

import com.univol.model.TipoOrganizacao;
import jakarta.validation.constraints.NotBlank;

public record OrganizacaoRequestDTO(

        @NotBlank
        String nome,
        @NotBlank
        TipoOrganizacao tipo,
        String contato
) {
}
