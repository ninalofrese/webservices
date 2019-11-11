package com.example.filmespopulares.repository;

import android.content.Context;

import com.example.filmespopulares.data.local.Database;
import com.example.filmespopulares.data.local.FilmesDao;
import com.example.filmespopulares.model.Favorito;
import com.example.filmespopulares.model.Filme;
import com.example.filmespopulares.model.FilmesPopulares;
import com.example.filmespopulares.model.Trailers;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.Single;

import static com.example.filmespopulares.data.remote.RetrofitService.getApiService;

public class FilmesRepository {

    public Flowable<FilmesPopulares> getFilmes(String apiKey, String idioma, int pagina) {
        return getApiService().getAllFilmes(apiKey, idioma, pagina);
    }

    public Flowable<Trailers> getTrailers(Long id, String apiKey) {
        return getApiService().getVideos(id, apiKey);
    }


    public Flowable<List<Filme>> getFilmesLocais(Context context) {
        Database room = Database.getDatabase(context);
        FilmesDao filmesDao = room.filmesDao();
        return filmesDao.getAllFilmes();
    }

    public Flowable<List<Favorito>> getFavoritos(Context context) {
        Database room = Database.getDatabase(context);
        FilmesDao filmesDao = room.filmesDao();
        return filmesDao.listaFavoritos();
    }

    public Observable<Filme> getFavoritoById(Context context, long id) {
        Database room = Database.getDatabase(context);
        FilmesDao filmesDao = room.filmesDao();

        return filmesDao.getFavoritoById(id);
    }

    public Single<Integer> checkFavoritoById(Context context, long id) {
        Database room = Database.getDatabase(context);
        FilmesDao filmesDao = room.filmesDao();

        return filmesDao.checkFavoritoById(id);
    }

    public void insertFavorito(Context context, Favorito favorito) {
        Database room = Database.getDatabase(context);
        FilmesDao filmesDao = room.filmesDao();
        filmesDao.insereFavorito(favorito);
    }

    public void deletarFavorito(Context context, Favorito favorito) {
        Database room = Database.getDatabase(context);
        FilmesDao filmesDao = room.filmesDao();
        filmesDao.deleteFavorito(favorito);
    }

}
