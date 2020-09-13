package br.com.ceeprecycleview.ui.activity;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import br.com.ceeprecycleview.R;
import br.com.ceeprecycleview.dao.NotaDAO;
import br.com.ceeprecycleview.model.Nota;
import br.com.ceeprecycleview.ui.recyclerview.adapter.ListaNotasAdapter;
import br.com.ceeprecycleview.ui.recyclerview.adapter.listener.OnItemClickListener;
import br.com.ceeprecycleview.ui.recyclerview.helper.callback.NotaItemTouchHelper;

import static br.com.ceeprecycleview.ui.activity.NotaActivityConstantes.CHAVE_NOTA;
import static br.com.ceeprecycleview.ui.activity.NotaActivityConstantes.CHAVE_POSICAO;
import static br.com.ceeprecycleview.ui.activity.NotaActivityConstantes.POSICAO_INVALIDA;
import static br.com.ceeprecycleview.ui.activity.NotaActivityConstantes.REQUEST_CODE_ALTERA_NOTA;
import static br.com.ceeprecycleview.ui.activity.NotaActivityConstantes.RESQUEST_CODE_NOVA_NOTA;

public class ListaNotasActivity extends AppCompatActivity {


    public static final String NOTAS = "Notas";
    private ListaNotasAdapter adapter;
    private NotaDAO dao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_notas);
        setTitle(NOTAS);
        List<Nota> notas = getNotas();
        configuraRecyclerView(notas);
        configuraBotaoNovaNota();
    }

    private List<Nota> getNotas() {
        dao = new NotaDAO();
        for (int i = 0; i < 10; i++) {
            dao.insere(new Nota("Nota " + (i + 1), "Descricao " + (i + 1)));
        }
        return dao.todos();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultadoNovaNota(requestCode, data)) {
            if (resultadoOk(resultCode)) {
                Nota nota = (Nota) data.getSerializableExtra(CHAVE_NOTA);
                adiciona(nota);
            }
        }
        if (resultadoAlteraNota(requestCode, data)) {
            if (resultadoOk(resultCode)) {
                Nota notaRecebida = (Nota) data.getSerializableExtra(CHAVE_NOTA);
                int posicaoRecebida = data.getIntExtra(CHAVE_POSICAO, POSICAO_INVALIDA);
                if (posicaoValida(posicaoRecebida)) {
                    altera(notaRecebida, posicaoRecebida);
                } else {
                    Toast.makeText(this, "Ocorreu um problema na alteração de nota", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    private void altera(Nota nota, int posicao) {
        dao.altera(posicao, nota);
        adapter.altera(posicao, nota);
    }

    private boolean posicaoValida(int posicaoRecebida) {
        return posicaoRecebida > POSICAO_INVALIDA;
    }

    private boolean resultadoAlteraNota(int requestCode, @Nullable Intent data) {
        return requestCodeAlteraNota(requestCode) &&
                data.hasExtra(CHAVE_NOTA);
    }

    private boolean requestCodeAlteraNota(int requestCode) {
        return requestCode == REQUEST_CODE_ALTERA_NOTA;
    }

    private void adiciona(Nota nota) {
        dao.insere(nota);
        adapter.adiciona(nota);
    }

    private boolean resultadoNovaNota(int requestCode, @Nullable Intent data) {
        return requestCodeNovaNota(requestCode) &&
                data.hasExtra(CHAVE_NOTA);
    }

    private boolean resultadoOk(int resultCode) {
        return resultCode == Activity.RESULT_OK;
    }

    private boolean requestCodeNovaNota(int requestCode) {
        return requestCode == RESQUEST_CODE_NOVA_NOTA;
    }

    private void configuraBotaoNovaNota() {
        TextView botaoNovaNota = findViewById(R.id.lista_notas_insere_nota);
        botaoNovaNota.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abreFormularioNotaActivityInsere();
            }
        });
    }

    private void abreFormularioNotaActivityInsere() {
        Intent intent = new Intent(ListaNotasActivity.this, FormularioNotaActivity.class);
        startActivityForResult(intent, RESQUEST_CODE_NOVA_NOTA);
    }

    private void configuraRecyclerView(List<Nota> notas) {
        RecyclerView listaNotas = findViewById(R.id.listaNotasRecycler);
        configuraAdapter(notas, listaNotas);
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new NotaItemTouchHelper());
        itemTouchHelper.attachToRecyclerView(listaNotas);
    }

    private void configuraAdapter(List<Nota> notas, RecyclerView listaNotas) {
        adapter = new ListaNotasAdapter(this, notas);
        listaNotas.setAdapter(adapter);
        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(Nota nota, int position) {
                abreFormularioNotaActivityAltera(nota, position);
            }
        });
    }

    private void abreFormularioNotaActivityAltera(Nota nota, int position) {
        Intent intent = new Intent(ListaNotasActivity.this, FormularioNotaActivity.class);
        intent.putExtra(CHAVE_NOTA, nota);
        intent.putExtra(CHAVE_POSICAO, position);
        startActivityForResult(intent, REQUEST_CODE_ALTERA_NOTA);
    }
}