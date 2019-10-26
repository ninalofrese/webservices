package com.example.json.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

import com.example.json.R;
import com.example.json.model.Noticia;
import com.example.json.view.adapters.RecyclerAdapter;
import com.example.json.viewmodel.NoticiaViewModel;

import java.util.ArrayList;
import java.util.List;

public class NoticiaActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private NoticiaViewModel viewModel;
    private List<Noticia> listaNoticias = new ArrayList<>();
    private RecyclerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_noticia);

        initViews();

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        viewModel.buscaListaNoticias();

        viewModel.retornaNoticias().observe(this, noticiaList -> {
            adapter.update(noticiaList);
        });

        viewModel.retornaValorLoading().observe(this, loading -> {
            if (loading) {
                progressBar.setVisibility(View.VISIBLE);
            } else {
                progressBar.setVisibility(View.GONE);
            }
        });
    }

    public void initViews() {
        recyclerView = findViewById(R.id.recyclerView);
        progressBar = findViewById(R.id.progressBar);
        //inicializa o view model
        //viewModelProviders mantém o viewmodel funcionando enquanto a activity estiver ativa
        viewModel = ViewModelProviders.of(this).get(NoticiaViewModel.class);
        adapter = new RecyclerAdapter(listaNoticias);
    }
}
