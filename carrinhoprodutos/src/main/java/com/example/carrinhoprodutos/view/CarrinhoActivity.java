package com.example.carrinhoprodutos.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.icu.lang.UCharacter;
import android.os.Bundle;
import android.widget.TextView;

import com.example.carrinhoprodutos.R;
import com.example.carrinhoprodutos.model.Carrinho;
import com.example.carrinhoprodutos.model.Produto;
import com.example.carrinhoprodutos.view.adapter.CarrinhoAdapter;
import com.example.carrinhoprodutos.view.interfaces.RemoveFromCart;
import com.example.carrinhoprodutos.viewmodel.CarrinhoActivityViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static com.example.carrinhoprodutos.view.ProdutosActivity.CARRINHO;

public class CarrinhoActivity extends AppCompatActivity implements RemoveFromCart {
    private Carrinho carrinho;
    private List<Produto> carroProdutos = new ArrayList<>();
    private RecyclerView recyclerCarrinho;
    private TextView totalCarrinho;
    private CarrinhoAdapter adapter;
    private CarrinhoActivityViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carrinho);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        initViews();

        if (getIntent() != null && getIntent().getExtras() != null) {
            carrinho = getIntent().getExtras().getParcelable(CARRINHO);

            if (carrinho != null) {
                carroProdutos = carrinho.getProdutosCarrinho();
            }
        }

        adapter = new CarrinhoAdapter(carroProdutos, this);
        recyclerCarrinho.setAdapter(adapter);
        recyclerCarrinho.setLayoutManager(new LinearLayoutManager(this));

        totalCarrinho.setText(String.format(Locale.GERMAN, "R$ %.2f", viewModel.calcularTotal(carroProdutos)));

    }

    public void initViews() {
        recyclerCarrinho = findViewById(R.id.recycler_carrinho);
        totalCarrinho = findViewById(R.id.display_total_value);
        viewModel = ViewModelProviders.of(this).get(CarrinhoActivityViewModel.class);
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    @Override
    public void clickRemoveCart(Produto produto) {
        carroProdutos.remove(produto);
        adapter.atualizaLista(carroProdutos);
        totalCarrinho.setText(String.format(Locale.GERMAN, "R$ %.2f", viewModel.calcularTotal(carroProdutos)));
    }
}
