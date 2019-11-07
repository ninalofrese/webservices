package com.example.filmespopulares.viewmodel;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.filmespopulares.model.Filme;
import com.example.filmespopulares.repository.FilmesRepository;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class PopularesViewModel extends AndroidViewModel {
    private MutableLiveData<List<Filme>> listaFilme = new MutableLiveData<>();
    private MutableLiveData<Boolean> loading = new MutableLiveData<>();
    private CompositeDisposable disposable = new CompositeDisposable();
    private FilmesRepository repository = new FilmesRepository();


    public PopularesViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<List<Filme>> getFilmesPopulares() {
        return this.listaFilme;
    }

    public LiveData<Boolean> getLoading() {
        return this.loading;
    }

    public void getAllFilmesPopulares(String apiKey, String idioma, int pagina) {
        disposable.add(
                repository.getFilmes(apiKey, idioma, pagina)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .doOnSubscribe(subscription -> {
                            loading.setValue(true);
                        })
                        .doOnTerminate(() -> {
                            loading.setValue(false);
                        })
                        .subscribe(filmesPopulares -> {
                            listaFilme.setValue(filmesPopulares.getResults());
                        }, throwable -> {
                            Log.i("POPULAR", "getAllFilmesPopulares " + throwable.getMessage());
                        })
        );
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        disposable.clear();
    }
}
