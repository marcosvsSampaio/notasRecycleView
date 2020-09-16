package br.com.ceeprecycleview.ui.recyclerview.helper.callback;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import br.com.ceeprecycleview.dao.NotaDAO;
import br.com.ceeprecycleview.ui.recyclerview.adapter.ListaNotasAdapter;

public class NotaItemTouchHelper extends ItemTouchHelper.Callback {

    public final ListaNotasAdapter adapter;
    private final NotaDAO dao = new NotaDAO();

    public NotaItemTouchHelper(ListaNotasAdapter adapter) {
        this.adapter = adapter;
    }

    @Override
    public int getMovementFlags(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
        int movimentosDeslize = ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT;
        int movimentosOrdenar = ItemTouchHelper.UP | ItemTouchHelper.DOWN |
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT;
        return makeMovementFlags(movimentosOrdenar, movimentosDeslize);
    }

    @Override
    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
        int posicaoInicial = viewHolder.getAbsoluteAdapterPosition();
        int posicaoFinal = target.getAbsoluteAdapterPosition();
        trocaPosicaoNota(posicaoInicial, posicaoFinal);
        return true;
    }

    @Override
    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
        int posicaoNotaDeslizada = viewHolder.getAbsoluteAdapterPosition();
        removeNota(posicaoNotaDeslizada);
    }

    private void trocaPosicaoNota(int posicaoInicial, int posicaoFinal) {
        dao.troca(posicaoInicial, posicaoFinal);
        adapter.troca(posicaoInicial, posicaoFinal);
    }

    private void removeNota(int posicao) {
        dao.remove(posicao);
        adapter.remove(posicao);
    }
}
