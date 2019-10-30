package com.example.apifilmes.viewmodel;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.apifilmes.model.Filme;
import com.example.apifilmes.repository.FilmeRepository;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class FIlmeViewModel extends AndroidViewModel {
    private MutableLiveData<List<Filme>> listaFilme = new MutableLiveData<>();
    private MutableLiveData<Boolean> loading = new MutableLiveData<>();
    private CompositeDisposable disposable = new CompositeDisposable();
    private FilmeRepository repository = new FilmeRepository();

    public FIlmeViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<List<Filme>> getFilmes() {
        return this.listaFilme;
    }

    public LiveData<Boolean> getLoading() {
        return this.loading;
    }

    public void getAllFilmes(String apiKey, String idioma) {
        disposable.add(
                repository.getFilmes(apiKey, idioma)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .doOnSubscribe(disposable1 -> loading.setValue(true))
                        .doOnTerminate(() -> loading.setValue(false))
                        .subscribe(filmeResult -> {
                            listaFilme.setValue(filmeResult.getFilmes());
                        }, throwable -> {
                            Log.wtf("LOGJ", "erro" + throwable.getMessage());
                        })
        );
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        disposable.clear();
    }
}
