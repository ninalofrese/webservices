package com.example.carrinhoprodutos.view;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.carrinhoprodutos.R;
import com.example.carrinhoprodutos.model.Carrinho;
import com.example.carrinhoprodutos.model.Produto;

import java.util.ArrayList;
import java.util.List;

import static com.example.carrinhoprodutos.view.ProdutosActivity.CARRINHO;

public class CarrinhoActivity extends AppCompatActivity {
    Carrinho carrinho;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carrinho);



    }
}
