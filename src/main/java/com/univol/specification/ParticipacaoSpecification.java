package com.univol.specification;

import com.univol.dto.participacao.ParticipacaoFilter;
import com.univol.model.Participacao;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class ParticipacaoSpecification {

    public static Specification<Participacao> withFilters(ParticipacaoFilter filter) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (filter != null) {
                if (filter.pedidoId() != null) {
                    predicates.add(cb.equal(root.get("pedido").get("id"), filter.pedidoId()));
                }
                if (filter.voluntarioId() != null) {
                    predicates.add(cb.equal(root.get("voluntario").get("id"), filter.voluntarioId()));
                }
                if (filter.dataParticipacaoInicio() != null) {
                    predicates.add(cb.greaterThanOrEqualTo(root.get("dataParticipacao"), filter.dataParticipacaoInicio()));
                }
                if (filter.dataParticipacaoFim() != null) {
                    predicates.add(cb.lessThanOrEqualTo(root.get("dataParticipacao"), filter.dataParticipacaoFim()));
                }
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }


}
