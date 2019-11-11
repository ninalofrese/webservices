package com.example.filmespopulares.view.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.filmespopulares.R;
import com.example.filmespopulares.model.Filme;
import com.example.filmespopulares.view.interfaces.FilmeOnClick;
import com.squareup.picasso.Picasso;

import java.util.List;

public class RecyclerPopularesAdapter extends RecyclerView.Adapter<RecyclerPopularesAdapter.ViewHolder> {
    private List<Filme> filmesPopulares;
    private FilmeOnClick listener;

    public RecyclerPopularesAdapter(List<Filme> filmesPopulares, FilmeOnClick listener) {
        this.filmesPopulares = filmesPopulares;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_popular, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Filme filme = filmesPopulares.get(position);

        holder.onBind(filme);

        holder.itemView.setOnClickListener(view -> {
            listener.onClick(filme);
        });
    }

    @Override
    public int getItemCount() {
        return filmesPopulares.size();
    }

    public void atualizaPopulares(List<Filme> populares) {
//        filmesPopulares.clear();
//        filmesPopulares = populares;
//        notifyDataSetChanged();
        if (this.filmesPopulares.isEmpty()) {
            this.filmesPopulares = populares;
        } else {
            //se não for vazia, adicionar ao final da lista a novaLista
            this.filmesPopulares.addAll(populares);
        }
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView popularPoster;
        private TextView popularTitle;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            popularPoster = itemView.findViewById(R.id.item_popular_poster);
            popularTitle = itemView.findViewById(R.id.item_popular_title);
        }

        public void onBind(Filme filme) {
            Picasso.get().load("https://image.tmdb.org/t/p/w500/" + filme.getPosterPath()).into(popularPoster);
            popularTitle.setText(filme.getTitle());
        }
    }
}
