
package com.example.randomuser.model;

import com.google.gson.annotations.Expose;

public class Dob {

    @Expose
    private Long age;
    @Expose
    private String date;

    public Long getAge() {
        return age;
    }

    public void setAge(Long age) {
        this.age = age;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

}
