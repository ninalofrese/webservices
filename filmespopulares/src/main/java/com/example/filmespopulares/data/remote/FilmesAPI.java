package com.example.filmespopulares.data.remote;

import com.example.filmespopulares.model.FilmesPopulares;

import io.reactivex.Flowable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface FilmesAPI {
    @GET("movie/popular")
    Flowable<FilmesPopulares> getAllFilmes(@Query("api_key") String apiKey,
                                           @Query("language") String idioma,
                                           @Query("page") int pagina);
}
