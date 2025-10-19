package com.belec.app.gui;

import com.belec.app.Main;
import com.belec.app.data.Category;
import com.belec.app.data.User;
import com.belec.app.sql.Requests;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.util.Collection;

public class ConnectionComponent extends Pane {

    @FXML
    private TextField connection_Id;

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
    public void deleteAccount(){
        String mail = connection_Id.getText();
        Requests.deleteUser(mail);
    }

    @FXML
    public void returnToHome() {
        Main.getMainScene().setRoot(new HomeComponent());
    }

    public ConnectionComponent() {
        super();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/connection.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }
}
