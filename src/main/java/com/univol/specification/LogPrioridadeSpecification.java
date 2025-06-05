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

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}
