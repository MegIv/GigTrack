package com.sisteminformasi.gigtrack;

import com.google.gson.annotations.SerializedName;

public class Album {
    @SerializedName("cover_medium")
    private String coverMedium;

    public String getCoverMedium() {
        return coverMedium;
    }

    public void setCoverMedium(String coverMedium) {
        this.coverMedium = coverMedium;
    }
}
