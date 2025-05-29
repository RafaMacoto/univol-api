package com.univol.model;

public enum StatusPedido {

    ABERTO("Aberto"),
    EM_ANDAMENTO("Em andamento"),
    CONCLUIDO("Concluído");

    private final String descricao;

    StatusPedido(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}
