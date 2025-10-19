package com.belec.app.gui;

import com.belec.app.Main;
import com.belec.app.data.*;
import com.belec.app.sql.Requests;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.*;

public class SaleCreationComponent extends VBox {

    @FXML
    private CheckBox montante;

    @FXML
    private CheckBox revocable;

    @FXML
    private CheckBox limiteOffre;

    @FXML
    private CheckBox venteLimite;

    @FXML
    private Spinner<Integer> stock;

    @FXML
    private Spinner<Double> prixDescente;

    @FXML
    private ChoiceBox<String> product;

    @FXML
    private Spinner<Double> prixDepart;

    @FXML
    private Spinner<Integer> hours;

    @FXML
    private Spinner<Integer> minutes;

    @FXML
    private DatePicker date;

    private SalesRoom salesRoom;

    private Collection<Sale> sales;

    private HashMap<String, Integer> products;

    private ArrayList<Product> productsList;

    @FXML
    private void venteLimiteChanged() {
        if(venteLimite.isSelected()) {
            this.date.setDisable(false);
            this.hours.setDisable(false);
            this.minutes.setDisable(false);
        }
        else {
            this.date.setDisable(true);
            this.hours.setDisable(true);
            this.minutes.setDisable(true);
        }
    }

    @FXML
    public void onMontanteChanged() {
        if(montante.isSelected()) {
            this.prixDescente.setDisable(true);
        }
        else {
            this.prixDescente.setDisable(false);
        }
    }

    @FXML
    private void addNewSale(){
        boolean increase = this.montante.isSelected();
        boolean cancelable = this.revocable.isSelected();
        boolean limitedOffers = this.limiteOffre.isSelected();
        boolean venteLimite = this.venteLimite.isSelected();
        float startingPrice = this.prixDepart.getValue().floatValue();
        int quantity = this.stock.getValue();
        float decreaseAmount = !increase ? this.prixDescente.getValue().floatValue() : 0;
        if(this.product.getValue() == null){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText("Entrez un produit");
            alert.setContentText("La vente n'a pas été ajouté.");
            alert.showAndWait();
            return;
        }
        int productId = this.products.get(this.product.getValue());
        Timestamp dateSQL = this.date.getValue() != null ? Timestamp.valueOf(LocalDateTime.of(
                                                            this.date.getValue().getYear(),
                                                            this.date.getValue().getMonthValue(),
                                                            this.date.getValue().getDayOfMonth(),
                                                            hours.getValue(),
                                                            minutes.getValue())):null;
        Sale sale = null;
        if(venteLimite) {
            if(dateSQL == null) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erreur");
                alert.setHeaderText("Entrez une date limite");
                alert.setContentText("La vente n'a pas été ajouté.");
                alert.showAndWait();
                return;
            }
            sale = new LimitedSale(0, salesRoom.getId(), productId, Main.getMainUser().getMail(),
                    increase, cancelable, limitedOffers, startingPrice, quantity, dateSQL, decreaseAmount);
        }
        else {
            sale = new Sale(0, salesRoom.getId(), productId, Main.getMainUser().getMail(),
                    increase, cancelable, limitedOffers, startingPrice, quantity, decreaseAmount);
        }
        if(!Requests.pushNewSale(sale)) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText("Erreur lors de l'insertion de la vente");
            alert.setContentText("La vente n'a pas été ajouté.");
            alert.showAndWait();
        }
        else{
            Main.getMainScene().setRoot(new SalesRoomComponent(salesRoom));
        }
    }

    @FXML
    public void returnToSalesRoom() {
        Main.getMainScene().setRoot(new SalesRoomComponent(salesRoom));
    }

    public SaleCreationComponent(SalesRoom salesRoom) {
        this.salesRoom = salesRoom;
        this.sales = Requests.retrievesAllSales(salesRoom);
        this.products = new HashMap<>();
        Collection<Product> productsList = Requests.retrievesAllProducts(new Category(salesRoom.getCategory(), ""));
        for(Product product : productsList) {
            this.products.put(product.getName(), product.getId());
        }
        this.productsList = new ArrayList<>();
        this.productsList.addAll(productsList);
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/saleCreation.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            exception.printStackTrace();
        }
        if(!sales.isEmpty()) {
            Sale sale = sales.iterator().next();
            montante.setDisable(true);
            montante.setSelected(sale.isIncrease());
        }
        stock.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, Integer.MAX_VALUE, 1));
        prixDescente.setValueFactory(new SpinnerValueFactory.DoubleSpinnerValueFactory(1, Float.MAX_VALUE, 1));
        prixDepart.setValueFactory(new SpinnerValueFactory.DoubleSpinnerValueFactory(1, Float.MAX_VALUE, 1));
        hours.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 23, 0));
        minutes.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 59, 0));
        this.product.setItems(FXCollections.observableList(this.products.keySet().stream().toList()));
    }
}
