package com.example.apifilmes.view.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.apifilmes.R;
import com.example.apifilmes.model.Filme;
import com.example.apifilmes.view.interfaces.MovieOnClick;
import com.squareup.picasso.Picasso;

import java.util.List;

public class FilmesRetrofitAdapter extends RecyclerView.Adapter<FilmesRetrofitAdapter.ViewHolder> {
    private List<Filme> filmeList;
    private MovieOnClick listener;

    public FilmesRetrofitAdapter(List<Filme> filmeList, MovieOnClick listener) {
        this.filmeList = filmeList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public FilmesRetrofitAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_filme, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FilmesRetrofitAdapter.ViewHolder holder, int position) {
        Filme filme = filmeList.get(position);
        holder.onBind(filme);

        holder.itemView.setOnClickListener(view -> {
            listener.onClick(filme);
        });
    }

    @Override
    public int getItemCount() {
        return filmeList == null ? 0 : filmeList.size();
        //return filmeList.size();
    }

    public void atualizaLista(List<Filme> novaLista) {
//        this.filmeList.clear();
//        this.filmeList = novaLista;
//        notifyDataSetChanged();
        //se a lista for vazia, popular com a novaLista
        if (this.filmeList.isEmpty()) {
            this.filmeList = novaLista;
        } else {
            //se não for vazia, adicionar ao final da lista a novaLista
            this.filmeList.addAll(novaLista);
        }
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView imagemFilme;
        private TextView tituloFilme;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imagemFilme = itemView.findViewById(R.id.imgFilme);
            tituloFilme = itemView.findViewById(R.id.txtTitulo);
        }

        public void onBind(Filme filme) {

            //Picasso para carregar imagens
            //De acordo com a documentação
            Picasso.get().load("https://image.tmdb.org/t/p/w500/" + filme.getPosterPath()).into(imagemFilme);

            tituloFilme.setText(filme.getTitle());


        }
    }
}
