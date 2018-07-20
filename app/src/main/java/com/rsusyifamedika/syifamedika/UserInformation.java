package com.rsusyifamedika.syifamedika;

public class UserInformation {
    private String nama;
    private String alamat;
    private String norm;
    private String tempat;
    private String tgl_lahir;
    private String poliklinik;
    private String DokterSpesialis;
    private String status;
    private String waktu;
    private String Pesan;

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


    public void setPoliklinik(String poliklinik){
        this.poliklinik = poliklinik;
    }
    public String getPoliklinik() {
        return waktu;
    }



    public void setDokterSpesialis(String dokter_spesialis) {
        this.DokterSpesialis = dokter_spesialis;
    }
    public String getDokterSpesialis() {
        return DokterSpesialis;
    }


    public void setStatus(String dokter_spesialis) {
        this.status = status;
    }

    public String getSatus() {
            return status;
    }

    public void setWaktu(String waktu) {
        this.waktu = waktu;
    }
    public String getwaktu() {
        return waktu;
    }

}
