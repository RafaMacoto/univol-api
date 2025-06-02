package com.univol.specification;

import com.univol.dto.voluntario.VoluntarioFilter;
import com.univol.model.Voluntario;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class VoluntarioSpecification {

    public static Specification<Voluntario> withFilters(VoluntarioFilter filter) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (filter.id() != null) {
                predicates.add(cb.equal(root.get("id"), filter.id()));
            }

            if (filter.idUsuario() != null) {
                predicates.add(cb.equal(root.get("usuario").get("id"), filter.idUsuario()));
            }

            if (filter.disponibilidade() != null && !filter.disponibilidade().isBlank()) {
                predicates.add(cb.like(cb.lower(root.get("disponibilidade")), "%" + filter.disponibilidade().toLowerCase() + "%"));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}
