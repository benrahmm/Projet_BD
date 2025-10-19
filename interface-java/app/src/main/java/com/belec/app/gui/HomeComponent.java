package com.belec.app.gui;

import com.belec.app.Main;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;

import java.io.IOException;

public class HomeComponent extends Pane {

    @FXML
    public void accountCreation() {
        Main.getMainScene().setRoot(new AccountCreationComponent());
    }

    @FXML
    public void connection() {
        Main.getMainScene().setRoot(new ConnectionComponent());
    }

    public HomeComponent() {
        super();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/home.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            exception.printStackTrace();
        }

    }
}
