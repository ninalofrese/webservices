package com.example.carrinhoprodutos.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.carrinhoprodutos.model.Produto;
import com.example.carrinhoprodutos.repository.data.Database;
import com.example.carrinhoprodutos.repository.data.ProdutoDao;

public class NovoProdutoActivityViewModel extends AndroidViewModel {
    private MutableLiveData<Produto> produto = new MutableLiveData<>();
    private ProdutoDao produtoDao = Database.getDatabase(getApplication()).produtoDao();

    public NovoProdutoActivityViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<Produto> retornaProduto() {
        return this.produto;
    }

    public void insereProdutoBancoDeDados(Produto produto) {
        new Thread(() -> {
            if (produto != null) {
                produtoDao.insereProduto(produto);
            }
        }).start();

        this.produto.setValue(produto);
    }

    public void atualizaProdutoBanco(Produto produto) {
        new Thread(() -> {
            if (produto != null) {
                produtoDao.updateProduto(produto);
            }
        }).start();

        this.produto.setValue(produto);
    }
}
