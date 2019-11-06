package com.example.albumsapi.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.SearchView;

import com.example.albumsapi.R;
import com.example.albumsapi.model.Album;
import com.example.albumsapi.view.adapters.RecyclerAlbumAdapter;
import com.example.albumsapi.viewmodel.AlbumsActivityViewModel;

import java.util.ArrayList;
import java.util.List;

public class AlbumsActivity extends AppCompatActivity {
    private RecyclerView recyclerAlbum;
    private ProgressBar progressBar;
    private SearchView search;
    private RecyclerAlbumAdapter adapter;
    private List<Album> albumList = new ArrayList<>();
    private AlbumsActivityViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_albums);

        initViews();

        recyclerAlbum.setAdapter(adapter);
        recyclerAlbum.setLayoutManager(new GridLayoutManager(this, 2));

        //TODO: Implementar search

        viewModel.getAllAlbums(search.getQuery().toString());

        viewModel.getListAlbums().observe(this, albums -> {
            adapter.atualizaLista(albums);
        });

        viewModel.getLoading().observe(this, aBoolean -> {
            if (aBoolean) {
                progressBar.setVisibility(View.VISIBLE);
            } else {
                progressBar.setVisibility(View.GONE);
            }
        });

    }

    public void initViews() {
        recyclerAlbum = findViewById(R.id.recyclerViewAlbum);
        progressBar = findViewById(R.id.progress_bar);
        search = findViewById(R.id.search_artist);
        adapter = new RecyclerAlbumAdapter(albumList);
        viewModel = ViewModelProviders.of(this).get(AlbumsActivityViewModel.class);
    }
}
