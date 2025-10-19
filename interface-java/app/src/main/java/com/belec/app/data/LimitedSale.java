package com.belec.app.data;

import java.sql.Date;
import java.sql.Timestamp;

public final class LimitedSale extends Sale {

    private Timestamp endDate;

    public LimitedSale(int id, int saleRoomId, int productId, String owner, boolean increase, boolean cancelable, boolean limitedOffers,
                       float startingPrice, int quantity, Timestamp endTime, float priceDecreaseAmount) {
        super(id, saleRoomId, productId, owner, increase, cancelable, limitedOffers, startingPrice, quantity, priceDecreaseAmount);
        this.endDate = endTime;
    }

    public Timestamp getEndDate() {
        return endDate;
    }

    public void setEndDate(Timestamp endDate) {
        this.endDate = endDate;
    }
}
