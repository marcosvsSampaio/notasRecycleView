package br.com.ceeprecycleview.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import br.com.ceeprecycleview.R;
import br.com.ceeprecycleview.model.Nota;

import static br.com.ceeprecycleview.ui.activity.NotaActivityConstantes.CHAVE_NOTA;
import static br.com.ceeprecycleview.ui.activity.NotaActivityConstantes.CHAVE_POSICAO;
import static br.com.ceeprecycleview.ui.activity.NotaActivityConstantes.POSICAO_INVALIDA;

public class FormularioNotaActivity extends AppCompatActivity {

    public static final String TITULO_APPBAR_INSERE = "Insere Nota";
    public static final String TITULO_APPBAR_ALTERA = "Altera Nota";
    private EditText titulo;
    private EditText descricao;
    private int posicaoRecebida;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario_nota);
        setTitle(TITULO_APPBAR_INSERE);
        inicializaCampos();
        Intent dadosRecebidos = getIntent();
        if (dadosRecebidos.hasExtra(CHAVE_NOTA)) {
            setTitle(TITULO_APPBAR_ALTERA);
            Nota notaRecebida = (Nota) dadosRecebidos.getSerializableExtra(CHAVE_NOTA);
            posicaoRecebida = dadosRecebidos.getIntExtra(CHAVE_POSICAO, POSICAO_INVALIDA);
            preencheCampos(notaRecebida);
        }
    }

    private void preencheCampos(Nota nota) {
        titulo.setText(nota.getTitulo());
        descricao.setText(nota.getDescricao());
    }

    private void inicializaCampos() {
        titulo = findViewById(R.id.formulario_nota_titulo);
        descricao = findViewById(R.id.formulario_nota_descricao);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_formulario_nota, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (menuSalvar(item)) {
            Nota notaCriada = criaNota();
            retornaNota(notaCriada);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    private void retornaNota(Nota notaCriada) {
        Intent intent = new Intent();
        intent.putExtra(CHAVE_NOTA, notaCriada);
        intent.putExtra(CHAVE_POSICAO, posicaoRecebida);
        setResult(Activity.RESULT_OK, intent);
    }

    private Nota criaNota() {

        return new Nota(titulo.getText().toString(), descricao.getText().toString());
    }

    private boolean menuSalvar(@NonNull MenuItem item) {
        return item.getItemId() == R.id.menu_formulario_nota_salvar;
    }
}