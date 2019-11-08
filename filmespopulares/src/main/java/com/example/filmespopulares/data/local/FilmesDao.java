package com.example.filmespopulares.data.local;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.filmespopulares.model.Favorito;
import com.example.filmespopulares.model.Filme;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Observable;

@Dao
public interface FilmesDao {
    @Insert
    void insereFavorito(Favorito favorito);

    @Delete
    void deleteFavorito(Favorito favorito);

    @Query("SELECT * FROM filmes")
    Flowable<List<Filme>> getAllFilmes();

    @Query("SELECT * FROM filmes WHERE id == :id")
    Filme getById(long id);

    @Query("SELECT * FROM favoritos")
    Flowable<List<Favorito>> listaFavoritos();

    @Query("SELECT * FROM filmes WHERE id == :id")
    Observable<Filme> getFavoritoById(long id);


}
