package com.example.filmespopulares.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.filmespopulares.model.Favorito;
import com.example.filmespopulares.repository.FilmesRepository;

import java.util.List;

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
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .doOnSubscribe(subscription -> {
                            loading.setValue(true);
                        })
                        .doOnTerminate(() -> {
                            loading.setValue(false);
                        })
                        .subscribe(favoritos -> {
                            listaFilmes.setValue(favoritos);
                        }, throwable -> {
                            erro.setValue(throwable.getMessage());
                        })
        );
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        disposable.clear();
    }
}
