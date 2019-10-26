package com.example.json.view.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.json.R;
import com.example.json.model.Noticia;

import java.util.List;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {
    private List<Noticia> listaNoticias;

    public RecyclerAdapter(List<Noticia> listaNoticias) {
        this.listaNoticias = listaNoticias;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recycler, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Noticia noticia = listaNoticias.get(position);
        holder.onBind(noticia);

    }

    @Override
    public int getItemCount() {
        return listaNoticias.size();
    }

    public void update(List<Noticia> noticiaList) {
        this.listaNoticias.clear();
        this.listaNoticias = noticiaList;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView displayTitulo;
        private TextView displayDescricao;
        private TextView displayData;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            displayTitulo = itemView.findViewById(R.id.textview_title);
            displayDescricao = itemView.findViewById(R.id.textview_descricao);
            displayData = itemView.findViewById(R.id.textview_data);
        }

        public void onBind(Noticia noticia) {
            displayTitulo.setText(noticia.getTitle());
            displayDescricao.setText(noticia.getDescription());
            displayData.setText(noticia.getDate());
        }
    }
}
