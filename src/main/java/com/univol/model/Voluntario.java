package com.univol.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "T_VOLUNTARIO", uniqueConstraints = @UniqueConstraint(columnNames = "id_usuario"))
public class Voluntario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "id_usuario")
    private Usuario usuario;

    @NotBlank
    @Size(max = 100)
    private String disponibilidade;

    @OneToMany(mappedBy = "voluntario")
    private List<Participacao> participacoes = new ArrayList<>();
}
