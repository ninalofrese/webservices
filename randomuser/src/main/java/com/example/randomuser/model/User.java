
package com.example.randomuser.model;

import com.google.gson.annotations.Expose;

import java.util.List;

public class User {

    @Expose
    private Info info;
    @Expose
    private List<Result> results;

    public Info getInfo() {
        return info;
    }

    public void setInfo(Info info) {
        this.info = info;
    }

    public List<Result> getResults() {
        return results;
    }

    public void setResults(List<Result> results) {
        this.results = results;
    }

}
