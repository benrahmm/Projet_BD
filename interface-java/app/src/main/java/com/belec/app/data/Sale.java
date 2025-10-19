package com.belec.app.data;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

public sealed class Sale permits LimitedSale {

    protected int id;
    protected String mailOwner;
    protected boolean increase;
    protected boolean cancelable;
    protected boolean limitedOffers;
    protected float prixDepart;
    protected int quantity;
    protected float priceDecreaseAmount;
    protected int productId;
    protected int saleRoomId;

    public Sale(int id, int saleRoomId, int productId, String owner, boolean increase, boolean cancelable, boolean limitedOffers, float prixDepart, int quantity, float priceDecreaseAmount) {
        this.id = id;
        this.mailOwner = owner;
        this.increase = increase;
        this.cancelable = cancelable;
        this.limitedOffers = limitedOffers;
        this.prixDepart = prixDepart;
        this.quantity = quantity;
        this.priceDecreaseAmount = priceDecreaseAmount;
        this.productId = productId;
        this.saleRoomId = saleRoomId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getOwner() {
        return mailOwner;
    }

    public void setOwner(String owner) {
        this.mailOwner = owner;
    }

    public boolean isIncrease() {
        return increase;
    }

    public void setIncrease(boolean increase) {
        this.increase = increase;
    }

    public boolean isCancelable() {
        return cancelable;
    }

    public void setCancelable(boolean cancelable) {
        this.cancelable = cancelable;
    }

    public boolean isLimitedOffers() {
        return limitedOffers;
    }

    public void setLimitedOffers(boolean limitedOffers) {
        this.limitedOffers = limitedOffers;
    }

    public float getPrixDepart() {
        return prixDepart;
    }

    public void setPrixDepart(float prixDepart) {
        this.prixDepart = prixDepart;
    }

    public int getQuantity() {
        return quantity;
    }

    public float getPriceDecreaseAmount() {
        return priceDecreaseAmount;
    }

    public int getProductId() {
        return productId;
    }

    public int getSaleRoomId() {
        return saleRoomId;
    }
}
