package br.com.ceeprecycleview.ui.activity;


import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import br.com.ceeprecycleview.R;
import br.com.ceeprecycleview.dao.NotaDAO;
import br.com.ceeprecycleview.model.Nota;
import br.com.ceeprecycleview.ui.adapter.ListaNotasAdapter;

public class ListaNotasActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_notas);
        List<Nota> notas = notasDeExemplo();
        configuraRecyclerView(notas);
    }

    private List<Nota> notasDeExemplo() {
        NotaDAO dao = new NotaDAO();
        for (int i = 1; i <= 10000; i++){
            dao.insere(new Nota("Titulo" + i, "Descricao " + i));
        }
        return dao.todos();
    }

    private void configuraRecyclerView(List<Nota> notas) {
        RecyclerView listaNotas = findViewById(R.id.listaNotasRecycler);
        configuraAdapter(notas, listaNotas);
    }

    private void configuraAdapter(List<Nota> notas, RecyclerView listaNotas) {
        listaNotas.setAdapter(new ListaNotasAdapter(this, notas));
    }
}