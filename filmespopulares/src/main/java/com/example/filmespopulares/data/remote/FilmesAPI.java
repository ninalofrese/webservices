package com.example.filmespopulares.data.remote;

import com.example.filmespopulares.model.FilmesPopulares;
import com.example.filmespopulares.model.Trailers;

import io.reactivex.Flowable;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface FilmesAPI {
    @GET("movie/popular")
    Flowable<FilmesPopulares> getAllFilmes(@Query("api_key") String apiKey,
                                           @Query("language") String idioma,
                                           @Query("page") int pagina);

    @GET("movie/{movie_id}/videos")
    Flowable<Trailers> getVideos(@Path("movie_id") Long id,
                                 @Query("api_key") String apiKey);

}
