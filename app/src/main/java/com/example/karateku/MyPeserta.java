package com.example.karateku;

public class MyPeserta {

    String nama_atlet,ttl;

    public MyPeserta() {
    }

    public MyPeserta(String nama_atlet, String ttl) {
        this.nama_atlet = nama_atlet;
        this.ttl = ttl;
    }

    public String getNama_atlet() {
        return nama_atlet;
    }

    public void setNama_atlet(String nama_atlet) {
        this.nama_atlet = nama_atlet;
    }

    public String getTtl() {
        return ttl;
    }

    public void setTtl(String ttl) {
        this.ttl = ttl;
    }
}
