package com.univol.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum Prioridade {

    ALTA("Alta"),
    MEDIA("Média"),
    BAIXA("Baixa");

    private final String descricao;

    Prioridade(String descricao) {
        this.descricao = descricao;
    }

    @JsonValue
    public String getDescricao() {
        return descricao;
    }

    @JsonCreator
    public static Prioridade fromDescricao(String descricao) {
        for (Prioridade p : Prioridade.values()) {
            if (p.descricao.equalsIgnoreCase(descricao)) {
                return p;
            }
        }
        throw new IllegalArgumentException("Prioridade inválida: " + descricao);
    }
}
