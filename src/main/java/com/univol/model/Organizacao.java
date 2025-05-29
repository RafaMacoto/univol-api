package com.univol.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "T_ORGANIZACAO")
public class Organizacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(max = 100)
    private String nome;

    @NotNull
    @Enumerated(EnumType.STRING)
    private TipoOrganizacao tipo;

    @NotBlank
    @Size(max = 100)
    private String contato;

    @OneToMany(mappedBy = "organizacao")
    private List<Pedido> pedidos = new ArrayList<>();
}
