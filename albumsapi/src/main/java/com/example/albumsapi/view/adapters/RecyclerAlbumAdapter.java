package com.example.albumsapi.view.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.albumsapi.R;
import com.example.albumsapi.model.Album;
import com.squareup.picasso.Picasso;

import java.util.List;

public class RecyclerAlbumAdapter extends RecyclerView.Adapter<RecyclerAlbumAdapter.ViewHolder> {
    private List<Album> albumList;

    public RecyclerAlbumAdapter(List<Album> albumList) {
        this.albumList = albumList;
    }

    @NonNull
    @Override
    public RecyclerAlbumAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_album, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Album album = albumList.get(position);
        holder.onBind(album);
    }

    @Override
    public int getItemCount() {
        return albumList == null ? 0 : albumList.size();
        //return filmeList.size();
    }

    public void atualizaLista(List<Album> novaLista) {
//        this.filmeList.clear();
//        this.filmeList = novaLista;
//        notifyDataSetChanged();
        //se a lista for vazia, popular com a novaLista
        if (this.albumList.isEmpty()) {
            this.albumList = novaLista;
        } else {
            //se não for vazia, adicionar ao final da lista a novaLista
            this.albumList.addAll(novaLista);
        }
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView imagemAlbum;
        private TextView tituloAlbum;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imagemAlbum = itemView.findViewById(R.id.imgAlbum);
            tituloAlbum = itemView.findViewById(R.id.txtTituloAlbum);
        }

        public void onBind(Album album) {

            //Picasso para carregar imagens
            //De acordo com a documentação
            Picasso.get().load(album.getStrAlbumThumb()).into(imagemAlbum);

            tituloAlbum.setText(album.getStrAlbum());
        }
    }
}