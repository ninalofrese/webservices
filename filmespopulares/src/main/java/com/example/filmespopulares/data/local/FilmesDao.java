package com.example.filmespopulares.data.local;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Update;

import com.example.filmespopulares.model.Filme;

@Dao
public interface FilmesDao {
    @Insert
    void insereProduto(Filme produto);

    @Update
    void updateProduto(Filme produto);

    @Delete
    void deleteProduto(Filme produto);

//    @Query("SELECT * FROM filmes")
//    Flowable<List<Filme>> getAllProdutos();
//
//    @Query("SELECT * FROM filmes WHERE id == :id")
//    Filme getById(long id);
}
