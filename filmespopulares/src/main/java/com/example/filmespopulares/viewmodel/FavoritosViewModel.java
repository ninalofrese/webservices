package com.example.filmespopulares.viewmodel;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.filmespopulares.model.Favorito;
import com.example.filmespopulares.model.Filme;
import com.example.filmespopulares.repository.FilmesRepository;

import java.util.List;

import io.reactivex.BackpressureOverflowStrategy;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class FavoritosViewModel extends AndroidViewModel {
    private MutableLiveData<List<Favorito>> listaFilmes = new MutableLiveData<>();
    private MutableLiveData<Boolean> loading = new MutableLiveData<>();
    private MutableLiveData<String> erro = new MutableLiveData<>();
    private CompositeDisposable disposable = new CompositeDisposable();
    private FilmesRepository repository = new FilmesRepository();

    public FavoritosViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<List<Favorito>> getListaFilmes() {
        return this.listaFilmes;
    }

    public LiveData<Boolean> getLoading() {
        return this.loading;
    }

    public LiveData<String> getError() {
        return this.erro;
    }


    public void getAllFavoritos() {
        disposable.add(
                repository.getFavoritos(getApplication().getApplicationContext())
                        .onBackpressureBuffer(100, () -> {
                        }, BackpressureOverflowStrategy.DROP_OLDEST)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .doOnSubscribe(subscription -> {
                            loading.setValue(true);
                        })
                        .doOnTerminate(() -> {
                            loading.setValue(false);
//                            Log.i("CICLO", "TERMINATE carregado");
                        })
                        .subscribe(favoritos -> {
                            listaFilmes.setValue(favoritos);
                            //tira o loading ao carregar os itens
                            loading.setValue(false);
                        }, throwable -> {
                            erro.setValue(throwable.getMessage());
                        })
        );
    }

    public void removerFavorito(Favorito favorito) {
        //Favorito novoFavorito = new Favorito(filme.getId(), filme);
        new Thread(() -> {
            repository.deletarFavorito(getApplication().getApplicationContext(), favorito);
        }).start();
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        disposable.clear();
    }
}
