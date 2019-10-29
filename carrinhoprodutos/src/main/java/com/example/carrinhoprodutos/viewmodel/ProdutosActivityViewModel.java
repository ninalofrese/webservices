package com.example.carrinhoprodutos.viewmodel;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.carrinhoprodutos.model.Produto;
import com.example.carrinhoprodutos.repository.data.Database;
import com.example.carrinhoprodutos.repository.data.ProdutoDao;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class ProdutosActivityViewModel extends AndroidViewModel {
    private MutableLiveData<List<Produto>> listaProdutos = new MutableLiveData<>();
    private ProdutoDao produtoDao = Database.getDatabase(getApplication()).produtoDao();
    private CompositeDisposable disposable = new CompositeDisposable();

    public ProdutosActivityViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<List<Produto>> retornaListaProdutos() {
        return listaProdutos;
    }

    public void listarProdutos() {
        disposable.add(
                produtoDao.getAllProdutos()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(produtos -> {
                            listaProdutos.setValue(produtos);
                        }, throwable -> {
                            Log.wtf("LOG", "Listagem de todos os produtos " + throwable.getMessage());
                        })
        );
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        disposable.clear();
    }
}
