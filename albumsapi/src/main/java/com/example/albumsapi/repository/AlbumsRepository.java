package com.example.albumsapi.repository;

import com.example.albumsapi.model.Artista;

import io.reactivex.Flowable;

import static com.example.albumsapi.data.remote.RetrofitService.getApiService;

public class AlbumsRepository {

    public Flowable<Artista> getAlbumArtist(String banda) {
        return getApiService().getAlbumsFromArtist(banda);
    }

}
