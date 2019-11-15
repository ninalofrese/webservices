package br.com.digitalhouse.firebaseapp.nina.favorites.view;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import br.com.digitalhouse.firebaseapp.R;
import br.com.digitalhouse.firebaseapp.nina.adapters.FavoritesViewAdapter;
import br.com.digitalhouse.firebaseapp.nina.interfaces.FavoriteItemClick;
import br.com.digitalhouse.firebaseapp.nina.model.Result;

public class FavoritesActivity extends AppCompatActivity implements FavoriteItemClick {
    private RecyclerView recyclerView;
    private FavoritesViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorites);

        recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        adapter = new FavoritesViewAdapter(new ArrayList<>(), this);
        recyclerView.setAdapter(adapter);

        // TODO: Listar itens salvos no firebase database
    }

    @Override
    public void removeFavoriteClickListener(Result result) {

        // TODO: remover item do firebase database
    }
}
