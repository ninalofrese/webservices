package com.example.filmespopulares.model;

import androidx.room.Embedded;

public class FilmesFavoritos {

    @Embedded
    private Filme filme;

    @Embedded
    private Favorito favorito;
}
