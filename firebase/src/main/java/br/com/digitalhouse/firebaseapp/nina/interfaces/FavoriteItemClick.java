package br.com.digitalhouse.firebaseapp.nina.interfaces;

import br.com.digitalhouse.firebaseapp.nina.model.Result;

public interface FavoriteItemClick {

    void addFavoriteClickListener(Result result);
    void removeFavoriteClickListener(Result result);
}
