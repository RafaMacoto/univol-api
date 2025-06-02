package com.univol.repository;


import com.univol.model.Participacao;
import com.univol.model.Pedido;
import com.univol.model.Voluntario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface ParticipacaoRepository extends JpaRepository<Participacao, Long>, JpaSpecificationExecutor<Participacao> {

    Optional<Participacao> findByPedidoAndVoluntario(Pedido pedido, Voluntario voluntario);
}
