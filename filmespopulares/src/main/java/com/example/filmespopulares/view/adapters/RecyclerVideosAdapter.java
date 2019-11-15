package com.example.filmespopulares.view.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.lifecycle.Lifecycle;
import androidx.recyclerview.widget.RecyclerView;

import com.example.filmespopulares.R;
import com.example.filmespopulares.model.Video;
import com.example.filmespopulares.view.interfaces.FullScreenListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.YouTubePlayerFullScreenListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;

import java.util.List;

public class RecyclerVideosAdapter extends RecyclerView.Adapter<RecyclerVideosAdapter.ViewHolder> {
    private List<Video> videosFilme;
    private Lifecycle lifecycle;
    private FullScreenListener listenerFullScreen;

    public RecyclerVideosAdapter(List<Video> videosFilme, Lifecycle lifecycle, FullScreenListener listenerFullScreen) {
        this.videosFilme = videosFilme;
        this.lifecycle = lifecycle;
        this.listenerFullScreen = listenerFullScreen;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        YouTubePlayerView youTubePlayerView = (YouTubePlayerView) LayoutInflater.from(parent.getContext()).inflate(R.layout.item_video, parent, false);
        lifecycle.addObserver(youTubePlayerView);

        return new ViewHolder(youTubePlayerView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.cueVideo(videosFilme.get(position));
        //Video video = videosFilme.get(position);

        //holder.onBind(video);
    }

    @Override
    public int getItemCount() {
        return videosFilme == null ? 0 : videosFilme.size();
    }

    public void atualizaVideos(List<Video> listaAtual) {
        //se a lista for vazia, popular com a novaLista
        if (this.videosFilme.isEmpty()) {
            this.videosFilme = listaAtual;
        } else {
            //se não for vazia, adicionar ao final da lista a novaLista
            this.videosFilme.addAll(listaAtual);
        }
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        private YouTubePlayerView youTubePlayerView;
        private YouTubePlayer youTubePlayer;
        private String currentVideoId;

        public ViewHolder(YouTubePlayerView playerView) {
            super(playerView);

            youTubePlayerView = playerView.findViewById(R.id.trailer_video);

            youTubePlayerView.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
                @Override
                public void onReady(YouTubePlayer initializedYouTubePlayer) {
                    youTubePlayer = initializedYouTubePlayer;
                    youTubePlayer.cueVideo(currentVideoId, 0);
                }
            });

            youTubePlayerView.addFullScreenListener(new YouTubePlayerFullScreenListener() {
                @Override
                public void onYouTubePlayerEnterFullScreen() {
                    listenerFullScreen.enterFullScreen(youTubePlayerView);
                }

                @Override
                public void onYouTubePlayerExitFullScreen() {
                    listenerFullScreen.exitFullScreen(youTubePlayerView);
                }
            });

        }


        public void cueVideo(Video video) {
            currentVideoId = video.getKey();

            if (youTubePlayer == null)
                return;

            youTubePlayer.cueVideo(video.getKey(), 0);
        }

    }
}
