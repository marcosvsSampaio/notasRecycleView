package br.com.ceeprecycleview.ui.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;

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
        ListView listaNotas = findViewById(R.id.listView);

        NotaDAO dao = new NotaDAO();
        for (int i = 1; i <= 10000; i++){
            dao.insere(new Nota("Titulo" + i, "Descricao " + i));
        }

        List<Nota> notas = dao.todos();

        ListaNotasAdapter adapter = new ListaNotasAdapter(this, notas);
        listaNotas.setAdapter(adapter);
    }
}