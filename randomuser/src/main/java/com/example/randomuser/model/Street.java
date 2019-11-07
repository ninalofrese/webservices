
package com.example.randomuser.model;

import com.google.gson.annotations.Expose;

public class Street {

    @Expose
    private String name;
    @Expose
    private Long number;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getNumber() {
        return number;
    }

    public void setNumber(Long number) {
        this.number = number;
    }

}
