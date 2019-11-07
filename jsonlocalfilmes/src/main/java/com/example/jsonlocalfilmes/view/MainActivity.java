package com.example.jsonlocalfilmes.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

import com.example.jsonlocalfilmes.R;
import com.example.jsonlocalfilmes.model.Filme;
import com.example.jsonlocalfilmes.view.adapters.RecyclerViewAdapter;
import com.example.jsonlocalfilmes.viewmodel.MainActivityViewModel;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ProgressBar loading;
    private RecyclerViewAdapter adapter;
    private List<Filme> listaFilmes = new ArrayList<>();

    private MainActivityViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();

        viewModel.buscarListaFilmes();

        viewModel.getLoading().observe(this, aBoolean -> {
            if (aBoolean) {
                loading.setVisibility(View.VISIBLE);
            } else {
                loading.setVisibility(View.GONE);
            }
        });

        viewModel.retornaFilmes().observe(this, filmes -> {
            adapter.updateList(filmes);
        });
    }

    public void initViews() {
        recyclerView = findViewById(R.id.recycler_filmes);
        loading = findViewById(R.id.progressBar);
        viewModel = ViewModelProviders.of(this).get(MainActivityViewModel.class);
        adapter = new RecyclerViewAdapter(listaFilmes);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }
}
