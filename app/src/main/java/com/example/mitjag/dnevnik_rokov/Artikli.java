package com.example.mitjag.dnevnik_rokov;

/**
 * Created by Mitja G on 24.5.2016.
 */
public class Artikli {
    private String koda_izdelka;
    private String naziv;
    private String opis;

    public Artikli(String koda_izdelka, String naziv, String opis) {
        this.koda_izdelka = koda_izdelka;
        this.naziv = naziv;
        this.opis = opis;
    }

    public String getKoda_izdelka() {
        return koda_izdelka;
    }

    public void setKoda_izdelka(String koda_izdelka) {
        this.koda_izdelka = koda_izdelka;
    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    public String getOpis() {
        return opis;
    }

    public void setOpis(String opis) {
        this.opis = opis;
    }
}
