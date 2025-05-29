package com.univol.model;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "T_PEDIDO")
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Lob
    private String descricao;

    @Enumerated(EnumType.STRING)
    private Prioridade prioridade;

    @NotNull
    private LocalDate dataPedido = LocalDate.now();

    @NotNull
    @Enumerated(EnumType.STRING)
    private StatusPedido status;

    @ManyToOne
    @JoinColumn(name = "id_organizacao")
    private Organizacao organizacao;

    @OneToMany(mappedBy = "pedido")
    private List<Participacao> participacoes = new ArrayList<>();
}
