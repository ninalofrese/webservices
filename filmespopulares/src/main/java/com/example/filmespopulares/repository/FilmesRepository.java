package com.example.filmespopulares.repository;

import com.example.filmespopulares.model.FilmesPopulares;

import io.reactivex.Flowable;

import static com.example.filmespopulares.data.remote.RetrofitService.getApiService;

public class FilmesRepository {

    public Flowable<FilmesPopulares> getFilmes(String apiKey, String idioma, int pagina) {
        return getApiService().getAllFilmes(apiKey, idioma, pagina);
    }

}
