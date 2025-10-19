package com.belec.app.gui;

import com.belec.app.Main;
import com.belec.app.data.Category;
import com.belec.app.data.Sale;
import com.belec.app.data.SalesRoom;
import com.belec.app.sql.Requests;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.util.Collection;

public class SalesRoomComponent extends AnchorPane {

    @FXML
    private SplitPane salesList;

    private SalesRoom salesRoom;

    private Collection<Sale> sales;

    @FXML
    public void addSale() {
        Main.getMainScene().setRoot(new SaleCreationComponent(salesRoom));
    }

    @FXML
    public void returnAction() {
        Main.getMainScene().setRoot(new SalesRoomListComponent(new Category(salesRoom.getCategory(), "")));
    }

    public SalesRoomComponent(SalesRoom salesRoom) {
        this.sales = Requests.retrievesAllSales(salesRoom);
        this.salesRoom = salesRoom;
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/salesRoom.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            exception.printStackTrace();
        }
        salesList.getItems().clear();
        for(Sale sale:this.sales) {
            salesList.getItems().add(new SaleComponent(sale, salesRoom));
        }
    }
}
