package com.belec.app.data;

public class Product {
    private int id;
    private String name;
    private double costPrice;
    private String categoryName; // Correspond à Nom_categorie dans SQL

    public Product(int id, String name, double costPrice, String categoryName) {
        this.id = id;
        this.costPrice = costPrice;
        this.categoryName = categoryName;
        this.name = name;
    }

    // Getters et setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getCostPrice() {
        return costPrice;
    }

    public void setCostPrice(double costPrice) {
        if (costPrice <= 0) {
            throw new IllegalArgumentException("Cost price must be greater than 0");
        }
        this.costPrice = costPrice;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", costPrice=" + costPrice +
                ", categoryName='" + categoryName + '\'' +
                '}';
    }
}
