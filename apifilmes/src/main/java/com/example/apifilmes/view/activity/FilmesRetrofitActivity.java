package com.example.apifilmes.view.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

import com.example.apifilmes.R;
import com.example.apifilmes.model.Filme;
import com.example.apifilmes.view.adapter.FilmesRetrofitAdapter;
import com.example.apifilmes.view.interfaces.MovieOnClick;
import com.example.apifilmes.viewmodel.FilmeViewModel;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class FilmesRetrofitActivity extends AppCompatActivity implements MovieOnClick {
    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private List<Filme> listaFilmes = new ArrayList<>();
    private FilmesRetrofitAdapter adapter;
    private FilmeViewModel viewModel;
    private int pagina = 1;

    public static final String API_KEY = "bde8033d3274c91b292a5293c6349052";
    public static final String LANGUAGE = "pt-BR";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filmes_retrofit);

        initViews();

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        setScrollView(); //chama o scroll

        viewModel.getAllFilmes(API_KEY, LANGUAGE, pagina);

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
        adapter = new FilmesRetrofitAdapter(listaFilmes, this);
        viewModel = ViewModelProviders.of(this).get(FilmeViewModel.class);
    }

    @Override
    public void onClick(Filme filme) {
        Intent intent = new Intent(FilmesRetrofitActivity.this, DetalheActivity.class);
        Bundle bundle = new Bundle();
        bundle.putParcelable("Filme", filme);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    private void setScrollView() {
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();

                //número de itens visíveis na tela (com margem para 1 acima e 1 abaixo, por conta do recycler)
                int totalItemCount = layoutManager.getItemCount();
                int lastVisible = layoutManager.findLastVisibleItemPosition();
                boolean ultimoItem = lastVisible + 5 >= totalItemCount;

                if (totalItemCount > 0 && ultimoItem) {
                    pagina++;
                    viewModel.getAllFilmes(API_KEY, LANGUAGE, pagina);
                }
            }
        });
    }
}
