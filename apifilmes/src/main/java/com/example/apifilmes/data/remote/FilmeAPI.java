package com.example.apifilmes.data.remote;

import com.example.apifilmes.model.FilmeResult;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface FilmeAPI {
    @GET("movie/now_playing")
    Observable<FilmeResult> getAllFilmes(@Query("api_key") String apiKey,
                                         @Query("language") String idioma,
                                         @Query("page") int pagina);
}
