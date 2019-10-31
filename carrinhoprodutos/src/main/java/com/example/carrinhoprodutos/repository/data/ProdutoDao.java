package com.example.carrinhoprodutos.repository.data;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.carrinhoprodutos.model.Produto;

import java.util.List;

import io.reactivex.Observable;

@Dao
public interface ProdutoDao {

    @Insert
    void insereProduto(Produto produto);

    @Update
    void updateProduto(Produto produto);

    @Delete
    void deleteProduto(Produto produto);

    @Query("SELECT * FROM produtos")
    Observable<List<Produto>> getAllProdutos();

    @Query("SELECT * FROM produtos WHERE id == :id")
    Produto getById(long id);
}
