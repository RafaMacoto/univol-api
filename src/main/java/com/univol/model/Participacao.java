package com.univol.model;


import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Data
@Entity
@Table(name = "T_PARTICIPACAO", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"id_pedido", "id_voluntario"})
})
public class Participacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idParticipacao;

    @ManyToOne(optional = false)
    @JoinColumn(name = "id_pedido", nullable = false)
    private Pedido pedido;

    @ManyToOne(optional = false)
    @JoinColumn(name = "id_voluntario", nullable = false)
    private Voluntario voluntario;

    private LocalDate dataParticipacao = LocalDate.now();
}
