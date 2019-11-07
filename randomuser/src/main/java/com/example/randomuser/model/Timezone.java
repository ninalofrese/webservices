
package com.example.randomuser.model;

import com.google.gson.annotations.Expose;

public class Timezone {

    @Expose
    private String description;
    @Expose
    private String offset;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getOffset() {
        return offset;
    }

    public void setOffset(String offset) {
        this.offset = offset;
    }

}
