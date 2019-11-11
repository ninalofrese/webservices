package com.example.filmespopulares.data.local;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.filmespopulares.model.Favorito;
import com.example.filmespopulares.model.Filme;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.Single;

@Dao
public interface FilmesDao {
    @Insert
    void insereFavorito(Favorito favorito);

    @Delete
    void deleteFavorito(Favorito favorito);

    @Query("SELECT * FROM filmes limit 50")
    Flowable<List<Filme>> getAllFilmes();

    @Query("SELECT * FROM filmes WHERE id == :id")
    Filme getById(long id);

    @Query("SELECT * FROM favoritos")
    Flowable<List<Favorito>> listaFavoritos();

    @Query("SELECT * FROM favoritos WHERE id_filme == :id")
    Observable<Filme> getFavoritoById(long id);

    @Query("SELECT COUNT(*) FROM favoritos WHERE id_filme == :id LIMIT 1")
    Single<Integer> checkFavoritoById(long id);

    @Query("Delete from filmes")
    void deleteAll();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(List<Filme> filmes);
}
