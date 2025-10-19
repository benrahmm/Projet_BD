package com.belec.app.gui;

import com.belec.app.Main;
import com.belec.app.data.Offer;
import com.belec.app.data.Sale;
import com.belec.app.data.SalesRoom;
import com.belec.app.sql.Requests;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.BorderPane;

import java.io.IOException;
import java.util.Collection;

public class ShowOffersComponent extends BorderPane {

    private Sale sale;
    private SalesRoom salesRoom;

    @FXML
    private SplitPane offerList;

    @FXML
    private void returnToSales() {
        Main.getMainScene().setRoot(new SalesRoomComponent(salesRoom));
    }

    private Collection<Offer> offers;

    public ShowOffersComponent(Sale sale, SalesRoom salesRoom) {
        super();
        this.offers = Requests.getAllOfferOrderedByInterest(sale);
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/offersList.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            exception.printStackTrace();
        }
        this.sale = sale;
        this.salesRoom = salesRoom;
        this.offerList.getItems().clear();
        for (Offer offer : offers) {
            this.offerList.getItems().add(new OfferComponent(sale, salesRoom, offer, sale.getOwner()));
        }
    }
}
