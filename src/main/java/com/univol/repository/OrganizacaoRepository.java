package com.univol.repository;

import com.univol.model.Organizacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface OrganizacaoRepository extends JpaRepository<Organizacao, Long>, JpaSpecificationExecutor<Organizacao> {
}
