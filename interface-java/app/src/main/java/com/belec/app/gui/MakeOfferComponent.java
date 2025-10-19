package com.belec.app.gui;

import com.belec.app.Main;
import com.belec.app.data.Category;
import com.belec.app.data.Offer;
import com.belec.app.data.Sale;
import com.belec.app.data.SalesRoom;
import com.belec.app.sql.Requests;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.awt.event.ActionEvent;
import java.io.IOException;
import java.sql.Date;
import java.sql.Timestamp;

public class MakeOfferComponent extends Pane {

    @FXML
    private Spinner<Double> proposedPrice;

    @FXML
    private Spinner<Integer> quantity;

    private Sale sale;

    private SalesRoom salesRoom;

    @FXML
    private Label bestPrice;

    public MakeOfferComponent(Sale sale, SalesRoom salesRoom) {
        this.sale = sale;
        this.salesRoom = salesRoom;
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/offerPage.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            exception.printStackTrace();
        }
        this.proposedPrice.setValueFactory(new SpinnerValueFactory.DoubleSpinnerValueFactory(
                sale.getPrixDepart(), Double.MAX_VALUE));
        this.quantity.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, sale.getQuantity(), sale.getQuantity()));
        if(!sale.isIncrease()){
            this.proposedPrice.setValueFactory(new SpinnerValueFactory.DoubleSpinnerValueFactory(
                    sale.getPrixDepart(),
                    sale.getPrixDepart(),
                    sale.getPrixDepart()
            ));
            this.quantity.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(sale.getQuantity(),
                    sale.getQuantity(),
                    sale.getQuantity()));
            this.proposedPrice.setDisable(true);
            this.quantity.setDisable(true);
        }
        float bestOffer = Requests.retrieveBestOffer(sale.getId());
        if(bestOffer >= 0) {
            this.bestPrice.setText("Meilleur offre à " + bestOffer + "euros (Prix unitaire)");
        }
        else {
            this.bestPrice.setText("Aucune offre pour l'instant.");
        }
    }

    @FXML
    public void quitOfferPage() {
        Main.getMainScene().setRoot(new SalesRoomComponent(this.salesRoom));
    }

    @FXML
    public void offerPush() {
        double price = proposedPrice.getValue();
        int quantityValue = quantity.getValue();
        if(price <= 0 || quantityValue <= 0 || quantityValue > sale.getQuantity()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText("Entrez une offre valide");
            alert.setContentText("Le prix ou la quantité est inférieur ou égale à 0 ou supérieur au stock");
            alert.showAndWait();
            return;
        }
        Offer offer = new Offer(Main.getMainUser().getMail(), 0, new Timestamp(System.currentTimeMillis()),
                price, quantityValue, sale.getId());
        System.out.println(offer.getId());
        if(!Requests.pushNewOffer(offer)){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText("La vente est fermé.");
            alert.setContentText("Veuillez quitter la vente");
            alert.showAndWait();
            return;
        }
        Main.getMainScene().setRoot(new SalesRoomComponent(this.salesRoom));
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setHeaderText("L'offre est bien enregistré.");
        alert.setContentText("Votre offre a été enregistré");
        alert.showAndWait();
    }
}
