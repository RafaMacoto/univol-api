package com.univol.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "T_LogPrioridade")
public class LogPrioridade {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_pedido", nullable = false)
    private Pedido pedido;

    @Column(name = "prioridade_classificada", nullable = false)
    private String prioridadeClassificada;

    @Column(name = "modelo_ml", nullable = false)
    private String modeloMl;

    @Column(name = "data_classificacao")
    private LocalDateTime dataClassificacao = LocalDateTime.now();

}
