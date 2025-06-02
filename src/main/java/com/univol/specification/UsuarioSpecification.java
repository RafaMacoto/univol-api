package com.univol.specification;

import com.univol.dto.usuario.UsuarioFilter;
import com.univol.model.Usuario;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class UsuarioSpecification {

    public static Specification<Usuario> withFilters(UsuarioFilter filter) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (filter.nome() != null && !filter.nome().isBlank()) {
                predicates.add(cb.like(cb.lower(root.get("nome")), "%" + filter.nome().toLowerCase() + "%"));
            }

            if (filter.email() != null && !filter.email().isBlank()) {
                predicates.add(cb.like(cb.lower(root.get("email")), "%" + filter.email().toLowerCase() + "%"));
            }

            if (filter.telefone() != null && !filter.telefone().isBlank()) {
                predicates.add(cb.like(cb.lower(root.get("telefone")), "%" + filter.telefone().toLowerCase() + "%"));
            }


            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}
