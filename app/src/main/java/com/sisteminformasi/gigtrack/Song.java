package com.sisteminformasi.gigtrack;

import com.google.gson.annotations.SerializedName;

public class Song {
    @SerializedName("title")
    private String title;

    @SerializedName("artist")
    private Artist artist;

    @SerializedName("album")
    private Album album;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Artist getArtist() {
        return artist;
    }

    public void setArtist(Artist artist) {
        this.artist = artist;
    }

    public Album getAlbum() {
        return album;
    }

    public void setAlbum(Album album) {
        this.album = album;
    }
}
