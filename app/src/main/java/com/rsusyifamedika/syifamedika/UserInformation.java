package com.rsusyifamedika.syifamedika;

class UserInformation {
    private String nama;
    private String alamat;
    private String norm;
    private String tempat;
    private String tgl_lahir;
    private String poliklinik;

    public UserInformation(){

    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public String getNorm() {
        return norm;
    }

    public void setNorm(String norm) {
        this.norm = norm;
    }

    public String getTempat() {
        return tempat;
    }

    public void setTempat(String tempat) {
        this.tempat = tempat;
    }

    public String getTgl_lahir() {
        return tgl_lahir;
    }

    public void setTgl_lahir(String tgl_lahir) {
        this.tgl_lahir = tgl_lahir;
    }
    public void getPoliklinik(String poliklinik){
        this.poliklinik = poliklinik;
    }
}
