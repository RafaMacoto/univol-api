package com.univol.specification;

import com.univol.dto.pedido.PedidoFilter;
import com.univol.model.Pedido;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class PedidoSpecification {

    public static Specification<Pedido> withFilters(PedidoFilter filter) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (filter.id() != null) {
                predicates.add(cb.equal(root.get("id"), filter.id()));
            }

            if (filter.descricao() != null && !filter.descricao().isBlank()) {
                predicates.add(cb.like(cb.lower(root.get("descricao")), "%" + filter.descricao().toLowerCase() + "%"));
            }

            if (filter.prioridade() != null) {
                predicates.add(cb.equal(root.get("prioridade"), filter.prioridade()));
            }

            if (filter.status() != null) {
                predicates.add(cb.equal(root.get("status"), filter.status()));
            }

            if (filter.dataPedidoInicio() != null) {
                predicates.add(cb.greaterThanOrEqualTo(root.get("dataPedido"), filter.dataPedidoInicio()));
            }

            if (filter.dataPedidoFim() != null) {
                predicates.add(cb.lessThanOrEqualTo(root.get("dataPedido"), filter.dataPedidoFim()));
            }

            if (filter.idOrganizacao() != null) {
                predicates.add(cb.equal(root.get("organizacao").get("id"), filter.idOrganizacao()));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}
