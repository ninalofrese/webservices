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
import android.widget.Toast;

import com.example.filmespopulares.R;
import com.example.filmespopulares.model.Favorito;
import com.example.filmespopulares.model.Filme;
import com.example.filmespopulares.view.activity.DetalheActivity;
import com.example.filmespopulares.view.adapters.RecyclerFavoritosAdapter;
import com.example.filmespopulares.view.interfaces.FilmeOnClick;
import com.example.filmespopulares.view.interfaces.HeartOnClick;
import com.example.filmespopulares.viewmodel.FavoritosViewModel;

import java.util.ArrayList;
import java.util.List;

import static com.example.filmespopulares.view.fragment.PopularesFragment.FILME;

/**
 * A simple {@link Fragment} subclass.
 */
public class FavoritosFragment extends Fragment implements FilmeOnClick, HeartOnClick {
    private RecyclerView recyclerFavoritos;
    private ProgressBar progressFavoritos;
    private RecyclerFavoritosAdapter adapter;
    private List<Favorito> filmesFavoritos = new ArrayList<>();
    private FavoritosViewModel viewModel;

    public FavoritosFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_favoritos, container, false);
        initViews(view);

        viewModel.getAllFavoritos();

        viewModel.getListaFilmes().observe(this, filmes -> {
            adapter.atualizaListaFavoritos(filmes);
        });

        viewModel.getError().observe(this, s -> {
            Toast.makeText(getContext(), s, Toast.LENGTH_SHORT).show();
        });

        viewModel.getLoading().observe(this, aBoolean -> {
            if (aBoolean) {
                progressFavoritos.setVisibility(View.VISIBLE);
            } else {
                progressFavoritos.setVisibility(View.GONE);
            }
        });

        return view;
    }

    public void initViews(View view) {
        recyclerFavoritos = view.findViewById(R.id.recycler_favoritos);
        progressFavoritos = view.findViewById(R.id.progress_bar_favoritos);
        adapter = new RecyclerFavoritosAdapter(filmesFavoritos, this, this);
        viewModel = ViewModelProviders.of(this).get(FavoritosViewModel.class);
        recyclerFavoritos.setLayoutManager(new GridLayoutManager(getContext(), 2));
        recyclerFavoritos.setAdapter(adapter);
    }

    @Override
    public void onClick(Filme filme) {
        Intent intent = new Intent(getContext(), DetalheActivity.class);
        Bundle bundle = new Bundle();
        bundle.putParcelable(FILME, filme);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    @Override
    public void favoriteOnClick(Favorito favorito) {
        viewModel.removerFavorito(favorito);
    }
}
