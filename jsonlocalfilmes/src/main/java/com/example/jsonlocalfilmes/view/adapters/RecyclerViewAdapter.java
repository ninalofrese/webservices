package com.example.jsonlocalfilmes.view.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.jsonlocalfilmes.model.Filme;
import com.example.jsonlocalfilmes.R;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {
    private List<Filme> listaFilmes;

    public RecyclerViewAdapter(List<Filme> listaFilmes) {
        this.listaFilmes = listaFilmes;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_filme, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Filme filme = listaFilmes.get(position);
        holder.onBind(filme);
    }

    @Override
    public int getItemCount() {
        return listaFilmes.size();
    }

    public void updateList(List<Filme> listaAtual) {
        listaFilmes.clear();
        listaFilmes = listaAtual;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView displayTitle;
        private TextView displayData;
        private TextView displayDiretores;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            displayTitle = itemView.findViewById(R.id.item_titulo);
            displayData = itemView.findViewById(R.id.item_data);
            displayDiretores = itemView.findViewById(R.id.item_diretores);
        }

        public void onBind(Filme filme) {
            displayTitle.setText(filme.getTitulo());
            displayData.setText(filme.getData());
            displayDiretores.setText(filme.getDirecao());
        }
    }
}
