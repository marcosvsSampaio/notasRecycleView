package br.com.ceeprecycleview.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import br.com.ceeprecycleview.R;
import br.com.ceeprecycleview.model.Nota;

public class ListaNotasAdapter extends BaseAdapter {

    private final Context context;
    private final List<Nota> notas;

    public ListaNotasAdapter(Context context, List<Nota> notas) {
        this.context = context;
        this.notas = notas;
    }

    @Override
    public int getCount() {
        return notas.size();
    }

    @Override
    public Nota getItem(int position) {
        return notas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View viewCriada = LayoutInflater.from(context).inflate(R.layout.item_nota, parent, false);
        Nota nota = notas.get(position);

        mostraTitulo(viewCriada, nota);

        mostraDescricao(viewCriada, nota);
        return viewCriada;
    }

    private void mostraDescricao(View viewCriada, Nota nota) {
        TextView descricao = viewCriada.findViewById(R.id.item_nota_descricao);
        descricao.setText(nota.getDescricao());
    }

    private void mostraTitulo(View viewCriada, Nota nota) {
        TextView titulo = viewCriada.findViewById(R.id.item_nota_titulo);
        titulo.setText(nota.getTitulo());
    }
}
