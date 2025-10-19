package com.belec.app.gui;

import com.belec.app.Main;
import com.belec.app.data.Category;
import com.belec.app.data.SalesRoom;
import com.belec.app.data.User;
import com.belec.app.sql.Requests;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.util.Collection;

public class SalesRoomListComponent extends Pane {

    @FXML
    private TextField connection_Id;

    private Collection<SalesRoom> salesRooms;

    private Category category;

    @FXML
    private Label categoryLabel;

    @FXML
    public void connection() {
        String mail = connection_Id.getText();
        User currentUser = Requests.retrieveUser(mail);
        if(currentUser != null) {
            Main.setMainUser(currentUser);
            Main.getMainScene().setRoot(new CategoryListComponent());
        }
        else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText("Compte non trouvé");
            alert.setContentText("Entrez un email existant");
            alert.showAndWait();
        }
    }

    @FXML
    public void returnButton() {
        Main.setMainUser(null);
        Main.getMainScene().setRoot(new CategoryListComponent());
    }

    public SalesRoomListComponent(Category category) {
        this.salesRooms = Requests.retrievesAllSalesRoomsInCategory(category);
        this.category = category;

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/salesRoomsList.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            exception.printStackTrace();
        }
        this.categoryLabel.setText(category.getDescription());
        ObservableList<Node> children = ((VBox) this.getChildren().get(1)).getChildren();
        children.clear();
        for(SalesRoom salesRoom:salesRooms) {
            Button button = new Button("Salle de Vente: " + salesRoom.getId());
            button.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                    Main.getMainScene().setRoot(new SalesRoomComponent(salesRoom));
                }
            });
            children.add(button);
        }
    }
}
