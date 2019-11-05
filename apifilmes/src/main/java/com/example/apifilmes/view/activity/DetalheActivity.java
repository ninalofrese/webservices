package com.example.apifilmes.view.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.apifilmes.R;
import com.example.apifilmes.model.Filme;

import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;
import com.squareup.picasso.Picasso;

public class DetalheActivity extends AppCompatActivity {
    private YouTubePlayerView movieTrailer;
    private ImageView moviePoster;
    private TextView movieTitle;
    private TextView movieDate;
    private TextView movieSinopse;

    private String videoId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhe);

        initViews();

        if (getIntent() != null && getIntent().getExtras() != null) {
            Filme filme = getIntent().getParcelableExtra("Filme");

            //TODO: Implementar videos da API e dar innerjoin https://developers.themoviedb.org/3/movies/get-movie-videos
            if (filme.getVideo()) {
                videoId = "7f353euyRno";
            }

            Picasso.get().load("https://image.tmdb.org/t/p/w500/" + filme.getPosterPath()).into(moviePoster);
            movieTitle.setText(filme.getTitle());
            movieDate.setText(filme.getReleaseDate());
            movieSinopse.setText(filme.getOverview());
        }

        videoId = "7f353euyRno";

        initYouTubePlayerView();
    }

    public void initViews() {
        movieTrailer = findViewById(R.id.movie_trailer);
        moviePoster = findViewById(R.id.movie_poster);
        movieTitle = findViewById(R.id.movie_title);
        movieDate = findViewById(R.id.movie_date);
        movieSinopse = findViewById(R.id.movie_sinopse);
    }

    private void initYouTubePlayerView() {
        //Adiciona o youTubePlayerView como observador da interface LifecycleObserver
        getLifecycle().addObserver(movieTrailer);

        //Chama o método que configura o Picture in Picture
        initPictureInPicture(movieTrailer);

        //Carrega o vídeo, passando o ID e os segundos da onde o vídeo inicia
        movieTrailer.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
            @Override
            public void onReady(@NonNull YouTubePlayer youTubePlayer) {
                youTubePlayer.loadVideo(videoId, 0);
            }
        });

    }

    private void initPictureInPicture(YouTubePlayerView youTubePlayerView) {
        //Inicializa e seta o ícone
        ImageView pictureInPictureIcon = new ImageView(this);
        pictureInPictureIcon.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_picture_in_picture));

        pictureInPictureIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //checa a versão do Android
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    //checa se está habilitado
                    boolean supportsPIP = getPackageManager().hasSystemFeature(PackageManager.FEATURE_PICTURE_IN_PICTURE);
                    if (supportsPIP)
                        //chama o método nativo que gerencia o PiP
                        enterPictureInPictureMode();
                    moviePoster.setVisibility(View.GONE);
                } else {
                    new AlertDialog.Builder(getApplicationContext())
                            .setTitle("Can't enter picture in picture mode")
                            .setMessage("In order to enter picture in picture mode you need a SDK version >= N.")
                            .show();
                }
            }
        });
        //adiciona o ícone ao player
        youTubePlayerView.getPlayerUiController().addView(pictureInPictureIcon);
    }

    @Override
    public void onPictureInPictureModeChanged(boolean isInPictureInPictureMode, Configuration newConfig) {
        super.onPictureInPictureModeChanged(isInPictureInPictureMode, newConfig);

        //Checa se está no modo PiP
        if (isInPictureInPictureMode) {
            //Entra no modo fullscreen e esconde a UI
            movieTrailer.enterFullScreen();
            movieTrailer.getPlayerUiController().showUi(false);
        } else {
            //Sai do modo fullscreen e mostra a UI
            movieTrailer.exitFullScreen();
            movieTrailer.getPlayerUiController().showUi(true);
            moviePoster.setVisibility(View.VISIBLE);
        }
    }
}
