package com.univol.specification;

import com.univol.dto.prioridade.LogPrioridadeFilter;
import com.univol.model.LogPrioridade;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class LogPrioridadeSpecification {

    public static Specification<LogPrioridade> withFilters(LogPrioridadeFilter filter) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (filter.idPedido() != null) {
                predicates.add(cb.equal(root.get("pedido").get("id"), filter.idPedido()));
            }

            if (filter.prioridadeClassificada() != null && !filter.prioridadeClassificada().isBlank()) {
                predicates.add(cb.like(cb.lower(root.get("prioridadeClassificada")),
                        "%" + filter.prioridadeClassificada().toLowerCase() + "%"));
            }

            if (filter.modeloMl() != null && !filter.modeloMl().isBlank()) {
                predicates.add(cb.like(cb.lower(root.get("modeloMl")),
                        "%" + filter.modeloMl().toLowerCase() + "%"));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}
