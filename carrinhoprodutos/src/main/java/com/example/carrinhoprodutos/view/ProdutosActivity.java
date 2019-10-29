package com.example.carrinhoprodutos.view;

import android.content.Intent;
import android.os.Bundle;

import com.example.carrinhoprodutos.R;
import com.example.carrinhoprodutos.model.Produto;
import com.example.carrinhoprodutos.repository.data.ProdutoDao;
import com.example.carrinhoprodutos.view.adapter.ProdutosAdapter;
import com.example.carrinhoprodutos.view.interfaces.ItemsToCart;
import com.example.carrinhoprodutos.view.interfaces.OnItemClickListener;
import com.example.carrinhoprodutos.viewmodel.ProdutosActivityViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Parcelable;
import android.view.ActionMode;
import android.view.MotionEvent;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;

public class ProdutosActivity extends AppCompatActivity implements ActionMode.Callback, ItemsToCart, OnItemClickListener {
    private RecyclerView recyclerView;
    private ProdutosAdapter adapter;
    private List<Produto> listaProduto = new ArrayList<>();
    private ActionMode actionMode;

    private ProdutosActivityViewModel viewModel;

    public static final String CARRINHO = "carrinho";
    public static final String PRODUTO = "produtoid";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_produtos);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        initViews();

        adapter = new ProdutosAdapter(listaProduto, this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        viewModel.listarProdutos();

        //o LiveData observa se tem alguma alteração nos dados do banco e avisa o adapter para ser atualizado
        viewModel.retornaListaProdutos().observe(this, produtos -> {
            adapter.atualizaLista(produtos);
        });

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(view -> {
            startActivity(new Intent(ProdutosActivity.this, NovoProdutoActivity.class));
        });
    }

    public void initViews() {
        recyclerView = findViewById(R.id.recycler_lista_produtos);
        viewModel = ViewModelProviders.of(this).get(ProdutosActivityViewModel.class);
    }

    private void myToggleSelection(int idx) {
        adapter.toggleSelection(idx);
        String title = getString(
                adapter.getSelectedItemCount(),
                "%d selecionados");
        actionMode.setTitle(title);
    }

    public void onSelect(MotionEvent event) {
        View view = recyclerView.findChildViewUnder(event.getX(), event.getY());

        if (actionMode != null) {
            return;
        }

        actionMode = startActionMode(ProdutosActivity.this);
        int idx = recyclerView.getChildAdapterPosition(view);
        myToggleSelection(idx);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_products, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
//        if (id == R.id.menu_add_cart) {
//            return true;
//        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public boolean onCreateActionMode(ActionMode actionMode, Menu menu) {
        return false;
    }

    @Override
    public boolean onPrepareActionMode(ActionMode actionMode, Menu menu) {
        return false;
    }

    @Override
    public boolean onActionItemClicked(ActionMode actionMode, MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.menu_add_cart:
                List<Integer> selectedItemPositions =
                        adapter.getSelectedItems();
                for (int i = selectedItemPositions.size() - 1;
                     i >= 0;
                     i--) {
                    adapter.sendToCart(selectedItemPositions.get(i));
                }
                actionMode.finish();
                return true;
            default:
                return false;
        }
    }

    @Override
    public void onDestroyActionMode(ActionMode actionMode) {
        this.actionMode = null;
        adapter.clearSelections();
    }

    @Override
    public void itemsToCart(List<Produto> listaProdutos) {
//        Intent intent = new Intent(ProdutosActivity.this, CarrinhoActivity.class);
//        Bundle bundle = new Bundle();
//        bundle.putParcelableArrayList(CARRINHO, (ArrayList<? extends Parcelable>) listaProdutos);
//        intent.putExtras(bundle);
//        startActivity(intent);
    }


    @Override
    public void onItemClick(Produto produto) {
        Intent intent = new Intent(ProdutosActivity.this, NovoProdutoActivity.class);
        Bundle bundle = new Bundle();
        bundle.putParcelable(PRODUTO, produto);
        intent.putExtras(bundle);
        startActivity(intent);
    }
}
