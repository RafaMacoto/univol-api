package com.univol.mapper;

import com.univol.dto.voluntario.VoluntarioRequestDTO;
import com.univol.dto.voluntario.VoluntarioResponseDTO;
import com.univol.model.Usuario;
import com.univol.model.Voluntario;
import org.springframework.stereotype.Component;

@Component
public class VoluntarioMapper {

    public static Voluntario toEntity(VoluntarioRequestDTO dto, Usuario usuario) {
        Voluntario voluntario = new Voluntario();
        voluntario.setDisponibilidade(dto.disponibilidade());
        voluntario.setUsuario(usuario);
        return voluntario;
    }

    public static VoluntarioResponseDTO toDTO(Voluntario voluntario) {
        return new VoluntarioResponseDTO(
                voluntario.getId(),
                voluntario.getDisponibilidade(),
                voluntario.getUsuario().getId()
        );
    }
}
