package com.belec.app.data;

public class Characteristics {

    private int id;
    private String nom;
    private String value;

    public Characteristics(int id, String nom, String value) {
        this.id = id;
        this.nom = nom;
        this.value = value;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
