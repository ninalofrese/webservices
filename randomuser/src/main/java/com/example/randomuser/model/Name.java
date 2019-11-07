
package com.example.randomuser.model;

import com.google.gson.annotations.Expose;

public class Name {

    @Expose
    private String first;
    @Expose
    private String last;
    @Expose
    private String title;

    public String getFirst() {
        return first;
    }

    public void setFirst(String first) {
        this.first = first;
    }

    public String getLast() {
        return last;
    }

    public void setLast(String last) {
        this.last = last;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return title + " " + first + " " + last;
    }
}
