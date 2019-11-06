package com.example.albumsapi.viewmodel;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.albumsapi.model.Album;
import com.example.albumsapi.model.Artista;
import com.example.albumsapi.repository.AlbumsRepository;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class AlbumsActivityViewModel extends AndroidViewModel {
    private MutableLiveData<List<Album>> listaAlbums = new MutableLiveData<>();
    private CompositeDisposable disposable = new CompositeDisposable();
    private AlbumsRepository repository = new AlbumsRepository();
    private MutableLiveData<Boolean> loading = new MutableLiveData<>();

    public AlbumsActivityViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<List<Album>> getListAlbums() {
        return this.listaAlbums;
    }

    public LiveData<Boolean> getLoading() {
        return this.loading;
    }

    public void getAllAlbums(String busca) {
        disposable.add(
                repository.getAlbumArtist(busca)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .doOnSubscribe(subscription -> {
                            loading.setValue(true);
                        })
                        .doOnTerminate(() -> {
                            loading.setValue(false);
                        })
                        .subscribe(artista1 -> {
                            listaAlbums.setValue(artista1.getAlbum());
                        }, throwable -> {
                            Log.i("ARTISTA", "getAllAlbums " + throwable.getMessage());
                        })
        );
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        disposable.clear();
    }
}
