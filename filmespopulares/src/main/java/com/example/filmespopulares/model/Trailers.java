
package com.example.filmespopulares.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Trailers {

    @Expose
    private Long id;
    @Expose
    @SerializedName("results")
    private List<Video> trailers;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Video> getResults() {
        return trailers;
    }

    public void setResults(List<Video> results) {
        this.trailers = results;
    }

}
