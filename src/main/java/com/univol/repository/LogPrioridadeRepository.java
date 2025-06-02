package com.univol.repository;

import com.univol.model.LogPrioridade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface LogPrioridadeRepository extends JpaRepository<LogPrioridade, Long>, JpaSpecificationExecutor<LogPrioridade> {
}
