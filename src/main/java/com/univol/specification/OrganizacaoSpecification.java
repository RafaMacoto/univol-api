package com.univol.specification;

import com.univol.dto.organizacao.OrganizacaoFilter;
import com.univol.model.Organizacao;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class OrganizacaoSpecification {

    public static Specification<Organizacao> withFilters(OrganizacaoFilter filter) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (filter.nome() != null && !filter.nome().isBlank()) {
                predicates.add(cb.like(cb.lower(root.get("nome")), "%" + filter.nome().toLowerCase() + "%"));
            }

            if (filter.tipo() != null && !filter.tipo().isBlank()) {
                predicates.add(cb.equal(root.get("tipo"), filter.tipo()));
            }

            if (filter.contato() != null && !filter.contato().isBlank()) {
                predicates.add(cb.like(cb.lower(root.get("contato")), "%" + filter.contato().toLowerCase() + "%"));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}
