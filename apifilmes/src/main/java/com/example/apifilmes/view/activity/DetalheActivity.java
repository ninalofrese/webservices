package com.example.apifilmes.view.activity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.apifilmes.R;
import com.example.apifilmes.model.Filme;
import com.squareup.picasso.Picasso;

public class DetalheActivity extends AppCompatActivity {
    private ImageView moviePoster;
    private TextView movieTitle;
    private TextView movieDate;
    private TextView movieSinopse;
    private ImageView movieBackdrop;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhe);

        initViews();

        if (getIntent() != null && getIntent().getExtras() != null) {
            Filme filme = getIntent().getParcelableExtra("Filme");

            Picasso.get().load("https://image.tmdb.org/t/p/w500/" + filme.getPosterPath()).into(moviePoster);
            movieTitle.setText(filme.getTitle());
            movieDate.setText(filme.getReleaseDate());
            movieSinopse.setText(filme.getOverview());
            Picasso.get().load("https://image.tmdb.org/t/p/w500/" + filme.getBackdropPath()).into(movieBackdrop);
        }

    }

    public void initViews() {
        moviePoster = findViewById(R.id.movie_poster);
        movieTitle = findViewById(R.id.movie_title);
        movieDate = findViewById(R.id.movie_date);
        movieSinopse = findViewById(R.id.movie_sinopse);
        movieBackdrop = findViewById(R.id.movie_backdrop);
    }


}
