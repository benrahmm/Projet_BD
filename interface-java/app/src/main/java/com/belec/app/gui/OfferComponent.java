package com.belec.app.gui;

import com.belec.app.Main;
import com.belec.app.data.Offer;
import com.belec.app.data.Product;
import com.belec.app.data.Sale;
import com.belec.app.data.SalesRoom;
import com.belec.app.sql.Requests;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

import java.io.IOException;

public class OfferComponent extends VBox {

    private Offer offer;

    @FXML
    private Label name;

    @FXML
    private Label date;

    @FXML
    private Label heure;

    @FXML
    private Label price;

    @FXML
    private Label quantite;

    @FXML
    private Button accept;

    @FXML
    private Button deny;

    private String saleOwner;

    private Sale sale;

    private SalesRoom salesRoom;

    @FXML
    public void acceptOffer() {
        if (Requests.acceptOffer(offer.getId())) {
            Sale newSale = Requests.retrieveSale(offer.getId());
            if (newSale != null) {
                Main.getMainScene().setRoot(new ShowOffersComponent(newSale, salesRoom));
            }
            else {
                Main.getMainScene().setRoot(new SalesRoomComponent(salesRoom));
            }
        } else {
            System.err.println("Failed to accept the offer.");
        }
    }

    @FXML
    public void denyOffer() {
        if (Requests.denyOffer(offer.getId())) {
            Main.getMainScene().setRoot(new ShowOffersComponent(sale, salesRoom));
        }
        else {
            System.err.println("Failed to deny the offer.");
        }
    }

    public OfferComponent(Sale sale, SalesRoom salesRoom, Offer offer, String saleOwner) {
        super();
        this.offer = offer;
        this.saleOwner = saleOwner;
        this.sale = sale;
        this.salesRoom = salesRoom;
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/offer.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            exception.printStackTrace();
        }
        Product product = Requests.retrievesProduct(sale.getProductId());
        this.name.setText("Offre par: " + offer.getRequest());
        this.date.setText("Date: " + offer.getDate());
        this.heure.setText("Heure: ");
        this.price.setText("Prix: " + offer.getPrice() +
                "Prix Unitaire: " + offer.getQuantity()/offer.getPrice() + " euros (Prix de revient: " + product.getCostPrice() + ")");
        this.quantite.setText("Quantite: " + offer.getQuantity());
        this.accept.setDisable(!this.saleOwner.equals(Main.getMainUser().getMail()));
        this.deny.setDisable(!this.saleOwner.equals(Main.getMainUser().getMail()) || !this.sale.isIncrease());
    }
}
