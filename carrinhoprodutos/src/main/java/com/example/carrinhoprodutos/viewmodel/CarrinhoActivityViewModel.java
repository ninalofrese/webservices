package com.example.carrinhoprodutos.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.example.carrinhoprodutos.model.Produto;

import java.util.List;

public class CarrinhoActivityViewModel extends AndroidViewModel {


    public CarrinhoActivityViewModel(@NonNull Application application) {
        super(application);
    }

    public Double calcularTotal(List<Produto> produtos) {
        Double soma = 0.0;

        for (Produto produto : produtos) {
            soma += produto.getPreco();
        }

        return soma;
    }
}
