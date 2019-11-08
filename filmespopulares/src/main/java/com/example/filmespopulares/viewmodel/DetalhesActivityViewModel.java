package com.example.filmespopulares.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.filmespopulares.model.Favorito;
import com.example.filmespopulares.model.Filme;
import com.example.filmespopulares.repository.FilmesRepository;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class DetalhesActivityViewModel extends AndroidViewModel {
    private MutableLiveData<Filme> filme = new MutableLiveData<>();
    private MutableLiveData<Boolean> favorito = new MutableLiveData<>();
    private MutableLiveData<String> erro = new MutableLiveData<>();
    private CompositeDisposable disposable = new CompositeDisposable();
    private FilmesRepository repository = new FilmesRepository();

    public DetalhesActivityViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<Boolean> getFavorito() {
        return this.favorito;
    }

    public void verificarFavorito(long id) {
        disposable.add(
                repository.getFavoritoById(getApplication().getApplicationContext(), id)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(filme1 -> {
                            if (filme1 != null) {
                                favorito.setValue(true);
                            } else {
                                favorito.setValue(false);
                            }

                        }, throwable -> {
                            erro.setValue(throwable.getMessage());
                        })
        );
    }

    public void inserirFavorito(Filme filme) {
        Favorito favorito = new Favorito(filme.getId());
        new Thread(() -> {
            repository.insertFavorito(getApplication().getApplicationContext(), favorito);
        }).start();
    }

    public void removerFavorito(Filme filme) {
        Favorito favorito = new Favorito(filme.getId());
        new Thread(() -> {
            repository.deletarFavorito(getApplication().getApplicationContext(), favorito);
        }).start();
    }
}
