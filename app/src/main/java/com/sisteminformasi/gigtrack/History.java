package com.sisteminformasi.gigtrack;

public class History {
    private int id;
    private String judul;
    private String artis;
    private String catatan;
    private String durasi;

    public History(int id, String judul, String artis, String catatan, String durasi) {
        this.id = id;
        this.judul = judul;
        this.artis = artis;
        this.catatan = catatan;
        this.durasi = durasi;
    }

    public int getId() { return id; }
    public String getJudul() { return judul; }
    public String getArtis() { return artis; }
    public String getCatatan() { return catatan; }
    public String getDurasi() { return durasi; }
}
