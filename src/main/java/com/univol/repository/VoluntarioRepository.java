package com.univol.repository;

import com.univol.model.Usuario;
import com.univol.model.Voluntario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface VoluntarioRepository extends JpaRepository<Voluntario, Long>, JpaSpecificationExecutor<Voluntario> {

    boolean existsByUsuario(Usuario usuario);
}
