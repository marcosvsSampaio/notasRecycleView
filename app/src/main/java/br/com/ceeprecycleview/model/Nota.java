package br.com.ceeprecycleview.model;

import java.io.Serializable;

public class Nota implements Serializable {
    private final String titulo;
    private final String descricao;

    public Nota(String titulo, String descricao) {
        this.titulo = titulo;
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }

    public String getTitulo() {
        return titulo;
    }
}
