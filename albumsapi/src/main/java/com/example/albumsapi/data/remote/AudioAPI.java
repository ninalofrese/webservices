package com.example.albumsapi.data.remote;

import com.example.albumsapi.model.Artista;

import io.reactivex.Flowable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface AudioAPI {

    @GET("searchalbum.php")
    Flowable<Artista> getAlbumsFromArtist(@Query("s") String banda);
}
