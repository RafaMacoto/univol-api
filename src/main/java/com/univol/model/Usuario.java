package com.univol.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "T_USUARIO")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(max = 100)
    private String nome;

    @NotBlank
    @Email
    @Size(max = 100)
    @Column(unique = true)
    private String email;

    @Size(max = 20)
    @Column(unique = true)
    private String telefone;

    @Size(max = 150)
    private String localizacao;

    @ElementCollection
    @CollectionTable(name = "usuario_habilidades", joinColumns = @JoinColumn(name = "usuario_id"))
    @Column(name = "habilidade")
    private List<String> habilidades = new ArrayList<>();
}
