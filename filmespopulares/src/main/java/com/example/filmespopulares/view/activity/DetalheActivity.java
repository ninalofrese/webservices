package com.example.filmespopulares.view.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.filmespopulares.R;
import com.example.filmespopulares.model.Filme;
import com.example.filmespopulares.viewmodel.DetalhesActivityViewModel;
import com.squareup.picasso.Picasso;

import static com.example.filmespopulares.view.fragment.PopularesFragment.FILME;

public class DetalheActivity extends AppCompatActivity {
    private ImageView movieBackdrop;
    private ImageView moviePoster;
    private TextView movieTitle;
    private TextView movieReleaseDate;
    private TextView movieOverview;
    private Toolbar toolbar;
    private Filme filme;

    private MenuItem favorito;
    private Menu menu;
    private DetalhesActivityViewModel viewModel;
    private boolean favorite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhe);

        initViews();

        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(false);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        if (getIntent() != null && getIntent().getExtras() != null) {
            filme = getIntent().getParcelableExtra(FILME);

            Picasso.get().load("https://image.tmdb.org/t/p/w500/" + filme.getBackdropPath()).into(movieBackdrop);
            Picasso.get().load("https://image.tmdb.org/t/p/w500/" + filme.getPosterPath()).into(moviePoster);
            movieTitle.setText(filme.getTitle());
            movieReleaseDate.setText(filme.getReleaseDate());
            movieOverview.setText(filme.getOverview());

            viewModel.verificarFavorito(filme.getId());

            viewModel.getFavorito().observe(this, aBoolean -> {
                favorite = aBoolean;
                if (aBoolean) {
                    menu.getItem(0).setIcon(ContextCompat.getDrawable(this, R.drawable.ic_favorite));
                } else {
                    menu.getItem(0).setIcon(ContextCompat.getDrawable(this, R.drawable.ic_not_favorite));
                }
            });

        }

        //TODO: Implementar vídeos
    }

    public void initViews() {
        toolbar = findViewById(R.id.toolbar_detalhes);
        viewModel = ViewModelProviders.of(this).get(DetalhesActivityViewModel.class);
        movieBackdrop = findViewById(R.id.movie_backdrop);
        moviePoster = findViewById(R.id.movie_poster);
        movieTitle = findViewById(R.id.movie_title);
        movieReleaseDate = findViewById(R.id.movie_date);
        movieOverview = findViewById(R.id.movie_sinopse);
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        this.menu = menu;
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.menu_favorito) {

            if (favorite) {
                menu.getItem(0).setIcon(ContextCompat.getDrawable(this, R.drawable.ic_not_favorite));
                viewModel.removerFavorito(filme);
                favorite = false;
            } else {
                menu.getItem(0).setIcon(ContextCompat.getDrawable(this, R.drawable.ic_favorite));
                viewModel.inserirFavorito(filme);
                favorite = true;
            }

            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
