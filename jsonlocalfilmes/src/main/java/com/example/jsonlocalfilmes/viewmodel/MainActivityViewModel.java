package com.example.jsonlocalfilmes.viewmodel;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.jsonlocalfilmes.model.Filme;
import com.example.jsonlocalfilmes.repository.LocalFilmesRepository;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class MainActivityViewModel extends AndroidViewModel {
    private MutableLiveData<List<Filme>> listFilme = new MutableLiveData<>();
    private MutableLiveData<Boolean> loading = new MutableLiveData<>();
    private LocalFilmesRepository repository = new LocalFilmesRepository();
    private CompositeDisposable disposable = new CompositeDisposable();

    public MainActivityViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<List<Filme>> retornaFilmes() {
        return this.listFilme;
    }

    public LiveData<Boolean> getLoading() {
        return this.loading;
    }

    public void buscarListaFilmes() {
        disposable.add(
                repository.obterListaFilmes(getApplication().getApplicationContext())
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .doOnSubscribe(disposable1 -> {
                            loading.setValue(true);
                        })
                        .doOnTerminate(() -> {
                            loading.setValue(false);
                        })
                        .subscribe(result -> {
                            listFilme.setValue(result.getFilmes());
                        }, throwable -> {
                            Log.i("FILMES", "busca filmes " + throwable.getMessage());
                        })
        );
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        disposable.clear();
    }
}
