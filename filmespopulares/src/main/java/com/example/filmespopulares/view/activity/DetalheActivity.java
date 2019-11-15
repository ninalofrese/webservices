package com.example.filmespopulares.view.activity;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.filmespopulares.R;
import com.example.filmespopulares.model.Filme;
import com.example.filmespopulares.model.Video;

import com.example.filmespopulares.util.FullScreenHelper;
import com.example.filmespopulares.view.adapters.RecyclerVideosAdapter;
import com.example.filmespopulares.view.interfaces.FullScreenListener;
import com.example.filmespopulares.viewmodel.DetalhesActivityViewModel;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import static com.example.filmespopulares.view.fragment.PopularesFragment.API_KEY;
import static com.example.filmespopulares.view.fragment.PopularesFragment.FILME;

public class DetalheActivity extends AppCompatActivity implements FullScreenListener {
    private ImageView movieBackdrop;
    private ImageView moviePoster;
    private TextView movieTitle;
    private TextView movieReleaseDate;
    private TextView movieOverview;
    private TextView labelVideos;
    private Toolbar toolbar;
    private Filme filme;

    private MenuItem favorito;
    private Menu menu;
    private DetalhesActivityViewModel viewModel;
    private boolean favorite;

    private RecyclerView recyclerVideos;
    private List<Video> videos = new ArrayList<>();
    private RecyclerVideosAdapter adapter;
    private ProgressBar progressVideos;
    private List<View> listaViews = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhe);

        initViews();
        adicionaViewsLista();

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
        }

        //TODO: Implementar vídeos
        viewModel.buscarVideos(filme.getId(), API_KEY);

        viewModel.getVideos().observe(this, videos1 -> {
            adapter.atualizaVideos(videos1);
        });

        viewModel.getLoading().observe(this, aBoolean -> {
            if (aBoolean) {
                progressVideos.setVisibility(View.VISIBLE);
            } else {
                progressVideos.setVisibility(View.GONE);
            }
        });

        viewModel.getError().observe(this, s -> {
            Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
        });

        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerVideos.setLayoutManager(layoutManager);
        recyclerVideos.setHasFixedSize(true);
        recyclerVideos.setAdapter(adapter);
    }

    public void initViews() {
        toolbar = findViewById(R.id.toolbar_detalhes);
        viewModel = ViewModelProviders.of(this).get(DetalhesActivityViewModel.class);
        movieBackdrop = findViewById(R.id.movie_backdrop);
        moviePoster = findViewById(R.id.movie_poster);
        movieTitle = findViewById(R.id.movie_title);
        movieReleaseDate = findViewById(R.id.movie_date);
        movieOverview = findViewById(R.id.movie_sinopse);
        labelVideos = findViewById(R.id.movie_videos);
        recyclerVideos = findViewById(R.id.recycler_videos);
        progressVideos = findViewById(R.id.progress_bar_video);
        adapter = new RecyclerVideosAdapter(videos, this.getLifecycle(), this);
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

        viewModel.verificarFavorito(filme.getId());

        viewModel.getFavorito().observe(this, aBoolean -> {
            favorite = aBoolean;
            if (aBoolean) {
                menu.getItem(0).setIcon(ContextCompat.getDrawable(this, R.drawable.ic_favorite));
            } else {
                menu.getItem(0).setIcon(ContextCompat.getDrawable(this, R.drawable.ic_not_favorite));
            }
        });

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


    @Override
    public void enterFullScreen(YouTubePlayerView playerView) {

        FullScreenHelper fullScreenHelper = new FullScreenHelper(this, listaViews);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        fullScreenHelper.enterFullScreen();

        ViewGroup.LayoutParams view = playerView.getLayoutParams();
        view.height = RelativeLayout.LayoutParams.MATCH_PARENT;
        view.width = RelativeLayout.LayoutParams.MATCH_PARENT;
        playerView.setLayoutParams(view);

//        recyclerVideos.setHasFixedSize(false);

    }

    @Override
    public void exitFullScreen(YouTubePlayerView playerView) {
        FullScreenHelper fullScreenHelper = new FullScreenHelper(this, listaViews);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        fullScreenHelper.exitFullScreen();

        ViewGroup.LayoutParams view = playerView.getLayoutParams();
        view.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        view.width = 250;
        playerView.setLayoutParams(view);

//        recyclerVideos.setHasFixedSize(true);
    }

    public void adicionaViewsLista() {
        listaViews.add(toolbar);
        listaViews.add(movieTitle);
        listaViews.add(movieBackdrop);
        listaViews.add(moviePoster);
        listaViews.add(movieReleaseDate);
        listaViews.add(movieOverview);
        listaViews.add(labelVideos);
        //listaViews.add(recyclerVideos);
    }
}
