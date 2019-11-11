package com.example.filmespopulares.viewmodel;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.filmespopulares.data.local.Database;
import com.example.filmespopulares.data.local.FilmesDao;
import com.example.filmespopulares.model.Filme;
import com.example.filmespopulares.model.FilmesPopulares;
import com.example.filmespopulares.repository.FilmesRepository;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

import static com.example.filmespopulares.util.ConnectionUtil.isNetworkConnected;

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

    public void getLista(String apiKey, String idioma, int pagina) {
        if (isNetworkConnected(getApplication())) {
            getAllFilmesPopulares(apiKey, idioma, pagina);
        } else {
            getLocalFilmesPopulares();
        }
    }

    public void getAllFilmesPopulares(String apiKey, String idioma, int pagina) {
        disposable.add(
                repository.getFilmes(apiKey, idioma, pagina)
                        .subscribeOn(Schedulers.io())
                        .map(this::salvarNoLocal)
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

    public void getLocalFilmesPopulares() {
        disposable.add(
                repository.getFilmesLocais(getApplication().getApplicationContext())
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .doOnSubscribe(subscription -> {
                            loading.setValue(true);
                        })
                        .doOnTerminate(() -> {
                            loading.setValue(false);
                        })
                        .subscribe(filmes -> {
                            listaFilme.setValue(filmes);
                            loading.setValue(false);
                        }, throwable -> {
                            Log.i("POPULAR", "getFilmesLocais " + throwable.getMessage());
                        })
        );
    }

    private FilmesPopulares salvarNoLocal(FilmesPopulares populares) {
        FilmesDao dao = Database.getDatabase(getApplication().getApplicationContext()).filmesDao();

        dao.deleteAll();

        dao.insert(populares.getResults());

        return populares;
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        disposable.clear();
    }
}
