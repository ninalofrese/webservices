package com.example.filmespopulares.model;

import androidx.room.ColumnInfo;
import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import androidx.room.RoomWarnings;

@Entity(tableName = "favoritos")
@SuppressWarnings(RoomWarnings.PRIMARY_KEY_FROM_EMBEDDED_IS_DROPPED)
public class Favorito {

    @PrimaryKey(autoGenerate = true)
    private long idFavorito;

    @ColumnInfo(name = "id_filme")
    private Long idFilme;

    @Embedded
    private Filme filme;

    public Favorito() {
    }

    @Ignore
    public Favorito(Long idFilme) {
        this.idFilme = idFilme;
    }

    @Ignore
    public Favorito(Long idFilme, Filme filme) {
        this.idFilme = idFilme;
        this.filme = filme;
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
