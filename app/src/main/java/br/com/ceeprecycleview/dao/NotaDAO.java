package br.com.ceeprecycleview.dao;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import br.com.ceeprecycleview.model.Nota;

public class NotaDAO {
    private final static ArrayList<Nota> notas = new ArrayList<>();

    public List<Nota> todos() {
        return (List<Nota>) notas.clone();
    }

    public void insere(Nota... notas) {
        NotaDAO.notas.addAll(Arrays.asList(notas));
    }

    public void altera(int posicao, Nota nota) {
        notas.set(posicao, nota);
    }

    public void remove(int posicao) {
        notas.remove(posicao);
    }

    public void removeTodos() {
        notas.clear();
    }
}