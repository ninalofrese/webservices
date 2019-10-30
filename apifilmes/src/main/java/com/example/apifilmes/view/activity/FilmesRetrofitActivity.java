package com.example.apifilmes.view.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

import com.example.apifilmes.R;
import com.example.apifilmes.model.Filme;
import com.example.apifilmes.view.adapter.FilmesRetrofitAdapter;
import com.example.apifilmes.viewmodel.FIlmeViewModel;

import java.util.ArrayList;
import java.util.List;

public class FilmesRetrofitActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private List<Filme> listaFilmes = new ArrayList<>();
    private FilmesRetrofitAdapter adapter;
    private FIlmeViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filmes_retrofit);

        initViews();

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        viewModel.getAllFilmes("bde8033d3274c91b292a5293c6349052", "pt-BR");

        viewModel.getFilmes().observe(this, filmes -> {
            adapter.atualizaLista(filmes);
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
        recyclerView = findViewById(R.id.recyclerViewFilmes);
        progressBar = findViewById(R.id.progress_bar);
        viewModel = ViewModelProviders.of(this).get(FIlmeViewModel.class);
        adapter = new FilmesRetrofitAdapter(listaFilmes);
    }
}
