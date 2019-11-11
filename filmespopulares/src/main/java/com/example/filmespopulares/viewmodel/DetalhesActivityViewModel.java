package com.example.filmespopulares.viewmodel;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.filmespopulares.model.Favorito;
import com.example.filmespopulares.model.Filme;
import com.example.filmespopulares.model.Video;
import com.example.filmespopulares.repository.FilmesRepository;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class DetalhesActivityViewModel extends AndroidViewModel {
    private MutableLiveData<Filme> filme = new MutableLiveData<>();
    private MutableLiveData<Boolean> favorito = new MutableLiveData<>();
    private MutableLiveData<Boolean> loading = new MutableLiveData<>();
    private MutableLiveData<String> erro = new MutableLiveData<>();
    private CompositeDisposable disposable = new CompositeDisposable();
    private FilmesRepository repository = new FilmesRepository();
    private MutableLiveData<List<Video>> listaVideos = new MutableLiveData<>();

    public DetalhesActivityViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<Boolean> getFavorito() {
        return this.favorito;
    }

    public LiveData<Boolean> getLoading() {
        return this.loading;
    }

    public LiveData<List<Video>> getVideos() {
        return this.listaVideos;
    }

    public LiveData<String> getError() {
        return this.erro;
    }

    public void verificarFavorito(long id) {
        disposable.add(
                repository.checkFavoritoById(getApplication().getApplicationContext(), id)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(integer -> {
                            if (integer == 1) {
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
        Favorito novoFavorito = new Favorito(filme.getId(), filme);
        new Thread(() -> {
            repository.insertFavorito(getApplication().getApplicationContext(), novoFavorito);
        }).start();
    }

    public void removerFavorito(Filme filme) {
        Favorito novoFavorito = new Favorito(filme.getId(), filme);
        new Thread(() -> {
            repository.deletarFavorito(getApplication().getApplicationContext(), novoFavorito);
        }).start();
    }

    public void buscarVideos(Long id, String apiKey) {
        disposable.add(
                repository.getTrailers(id, apiKey)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .doOnSubscribe(subscription -> {
                            loading.setValue(true);
                        })
                        .doOnTerminate(() -> {
                            loading.setValue(false);
                        })
                        .subscribe(trailers -> {
                            listaVideos.setValue(trailers.getResults());
                        }, throwable -> {
                            erro.setValue(throwable.getMessage());
                            Log.i("CICLO", "buscarVideos " + throwable.getMessage());
                        })
        );
    }
}
