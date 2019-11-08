package com.example.filmespopulares.view.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.filmespopulares.R;
import com.example.filmespopulares.model.Favorito;
import com.example.filmespopulares.model.Filme;
import com.example.filmespopulares.view.interfaces.FilmeOnClick;
import com.squareup.picasso.Picasso;

import java.util.List;

public class RecyclerFavoritosAdapter extends RecyclerView.Adapter<RecyclerFavoritosAdapter.ViewHolder> {
    private List<Favorito> filmesFavoritos;
    private FilmeOnClick listener;

    public RecyclerFavoritosAdapter(List<Favorito> filmesFavoritos, FilmeOnClick listener) {
        this.filmesFavoritos = filmesFavoritos;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_favorito, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Favorito favorito = filmesFavoritos.get(position);

        holder.onBind(favorito.getFilme());

        holder.itemView.setOnClickListener(view -> {
            listener.onClick(favorito.getFilme());
        });
    }

    @Override
    public int getItemCount() {
        return filmesFavoritos.size();
    }

    public void atualizaListaFavoritos(List<Favorito> filmes) {
        this.filmesFavoritos.clear();
        filmesFavoritos = filmes;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView favoritoPoster;
        private TextView favoritoTitle;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            favoritoPoster = itemView.findViewById(R.id.item_favorito_poster);
            favoritoTitle = itemView.findViewById(R.id.item_favorito_title);
        }

        public void onBind(Filme filme) {
            Picasso.get().load("https://image.tmdb.org/t/p/w500/" + filme.getPosterPath()).into(favoritoPoster);
            favoritoTitle.setText(filme.getTitle());
        }
    }
}
