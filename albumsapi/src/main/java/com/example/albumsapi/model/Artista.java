
package com.example.albumsapi.model;

import com.google.gson.annotations.Expose;

import java.util.List;

public class Artista {

    @Expose
    private List<Album> album;

    public List<Album> getAlbum() {
        return album;
    }

    public void setAlbum(List<Album> album) {
        this.album = album;
    }

}
