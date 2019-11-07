package com.example.filmespopulares.view.fragment;


import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.example.filmespopulares.R;
import com.example.filmespopulares.model.Filme;
import com.example.filmespopulares.view.activity.DetalheActivity;
import com.example.filmespopulares.view.adapters.RecyclerPopularesAdapter;
import com.example.filmespopulares.view.interfaces.FilmeOnClick;
import com.example.filmespopulares.viewmodel.PopularesViewModel;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class PopularesFragment extends Fragment implements FilmeOnClick {
    private RecyclerView recyclerPopulares;
    private ProgressBar progressPopulares;
    private RecyclerPopularesAdapter adapter;
    private List<Filme> listaPopulares = new ArrayList<>();
    private PopularesViewModel viewModel;

    public static final String FILME = "filme";

    public static final String API_KEY = "bde8033d3274c91b292a5293c6349052";
    public static final String LANGUAGE = "pt-BR";
    private int pagina = 1;

    public PopularesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_populares, container, false);

        initViews(view);

        viewModel.getAllFilmesPopulares(API_KEY, LANGUAGE, pagina);

        viewModel.getFilmesPopulares().observe(this, filmes -> {
            adapter.atualizaPopulares(filmes);
        });

        viewModel.getLoading().observe(this, aBoolean -> {
            if (aBoolean) {
                progressPopulares.setVisibility(View.VISIBLE);
            } else {
                progressPopulares.setVisibility(View.GONE);
            }
        });

        return view;
    }

    public void initViews(View view) {
        recyclerPopulares = view.findViewById(R.id.recycler_populares);
        progressPopulares = view.findViewById(R.id.progress_bar_populares);
        viewModel = ViewModelProviders.of(this).get(PopularesViewModel.class);
        adapter = new RecyclerPopularesAdapter(listaPopulares, this);
        recyclerPopulares.setLayoutManager(new GridLayoutManager(getContext(), 2));
        recyclerPopulares.setAdapter(adapter);
    }

    @Override
    public void onClick(Filme filme) {
        Intent intent = new Intent(getContext(), DetalheActivity.class);
        Bundle bundle = new Bundle();
        bundle.putParcelable(FILME, filme);
        intent.putExtras(bundle);
        startActivity(intent);
    }
}
