package com.belec.app.data;

import java.sql.Date;
import java.sql.Timestamp;

public class Offer {

    private String mail;
    private int id;
    private Timestamp date;
    private double price;
    private int quantity;
    private int idSale;

    public Offer(String mail, int id, Timestamp date, double price, int quantity, int sale) {
        this.mail = mail;
        this.id = id;
        this.date = date;
        this.price = price;
        this.quantity = quantity;
        this.idSale = sale;
    }

    public String getRequest() {
        return mail;
    }

    public void setRequest(String request) {
        this.mail = request;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setIdSale(int sale) {
        this.idSale = sale;
    }

    public int getIdSale() {
        return idSale;
    }
}
