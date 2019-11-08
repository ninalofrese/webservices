package com.example.filmespopulares.model;

import androidx.room.ColumnInfo;
import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "favoritos")
public class Favorito {

    @PrimaryKey(autoGenerate = true)
    private long idFavorito;

    @ColumnInfo(name = "id_filme")
    private Long idFilme;

    @Embedded(prefix = "filme_")
    private Filme filme;

    public Favorito() {
    }

    @Ignore
    public Favorito(Long idFilme) {
        this.idFilme = idFilme;
    }

    public long getIdFavorito() {
        return idFavorito;
    }

    public void setIdFavorito(long idFavorito) {
        this.idFavorito = idFavorito;
    }

    public Long getIdFilme() {
        return idFilme;
    }

    public void setIdFilme(Long idFilme) {
        this.idFilme = idFilme;
    }

    public Filme getFilme() {
        return filme;
    }

    public void setFilme(Filme filme) {
        this.filme = filme;
    }
}
