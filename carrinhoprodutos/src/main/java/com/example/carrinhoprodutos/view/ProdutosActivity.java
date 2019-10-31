package com.example.carrinhoprodutos.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.carrinhoprodutos.R;
import com.example.carrinhoprodutos.model.Carrinho;
import com.example.carrinhoprodutos.model.Produto;
import com.example.carrinhoprodutos.view.adapter.ProdutosAdapter;
import com.example.carrinhoprodutos.view.interfaces.DeleteItem;
import com.example.carrinhoprodutos.view.interfaces.ItemsToCart;
import com.example.carrinhoprodutos.view.interfaces.OnItemClickListener;
import com.example.carrinhoprodutos.viewmodel.ProdutosActivityViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

public class ProdutosActivity extends AppCompatActivity implements ItemsToCart, OnItemClickListener, DeleteItem {
    private RecyclerView recyclerView;
    private ProdutosAdapter adapter;
    private List<Produto> listaProduto = new ArrayList<>();
    private List<Produto> produtosCarrinho = new ArrayList<>();

    private ProdutosActivityViewModel viewModel;

    public static final String CARRINHO = "carrinho";
    public static final String PRODUTO = "produto";
    public static final String PROD_ID = "produtoid";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_produtos);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        initViews();
        setTitle("Produtos");

        adapter = new ProdutosAdapter(listaProduto, this, this, this);
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_products, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_carrinho:
                if (produtosCarrinho.size() != 0) {
                    Carrinho carrinho = new Carrinho(produtosCarrinho);
                    Intent intent = new Intent(ProdutosActivity.this, CarrinhoActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putParcelable(CARRINHO, carrinho);
                    intent.putExtras(bundle);
                    startActivity(intent);
                } else {
                    Snackbar.make(recyclerView, "Adicione primeiro um item `a lista", Snackbar.LENGTH_SHORT).show();
                }

                return false;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onItemClick(Produto produto) {
        Intent intent = new Intent(ProdutosActivity.this, NovoProdutoActivity.class);
        Bundle bundle = new Bundle();
        //bundle.putLong(PROD_ID, produto.getId());
        bundle.putParcelable(PRODUTO, produto);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    @Override
    public void addItemToCart(Produto produto) {
        produtosCarrinho.add(produto);
        Toast.makeText(this, "Item adicionado ao carrinho", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void removeItemFromDB(Produto produto) {
        viewModel.deleteItem(produto);
        viewModel.listarProdutos();
    }
}
