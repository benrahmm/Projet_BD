package com.belec.app.gui;

import com.belec.app.Main;
import com.belec.app.data.*;
import com.belec.app.sql.Requests;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class SaleComponent extends VBox {

    @FXML
    private Label productName;

    @FXML
    private Label price;

    @FXML
    private ListView<String> properties;

    @FXML
    private Button buyButton;

    @FXML
    private Button cancel;

    private Sale sale;

    private SalesRoom salesRoom;

    @FXML
    public void buyOffer() {
        Main.getMainScene().setRoot(new MakeOfferComponent(sale, salesRoom));
    }

    @FXML
    public void showOffers() {
        Main.getMainScene().setRoot(new ShowOffersComponent(sale, salesRoom));
    }

    @FXML
    public void cancelSale() {
        if(!Requests.deleteSale(sale)) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText("Erreur lors de la suppression de la vente.");
            alert.setContentText("La vente n'a pas été supprimée");
            alert.showAndWait();
        }
        else {
            Main.getMainScene().setRoot(new SalesRoomComponent(salesRoom));
        }
    }

    public SaleComponent(Sale sale, SalesRoom salesRoom) {
        super();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/vente.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            exception.printStackTrace();
        }
        this.sale = sale;
        this.salesRoom = salesRoom;
        Product p = Requests.retrievesProduct(sale.getProductId());
        this.productName.setText("Produit " + p.getName());
        this.price.setText("" + sale.getPrixDepart() + "$");
        this.cancel.setDisable(!sale.isCancelable());
        if(!this.sale.getOwner().equals(Main.getMainUser().getMail())) {
            this.cancel.setDisable(true);
        }
        Collection<Characteristics> characteristics = Requests.retrievesAllCharacteristics(p);
        List<String> chara = characteristics.stream().map(e -> e.getNom() + ": " + e.getValue()).toList();
        this.properties.setItems(FXCollections.observableList(chara));
    }
}
