package com.belec.app.gui;

import com.belec.app.Main;
import com.belec.app.data.User;
import com.belec.app.sql.Requests;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;

import java.io.IOException;

public class AccountCreationComponent extends Pane {

    @FXML
    private TextField mail;

    @FXML
    private TextField firstname;

    @FXML
    private TextField lastname;

    @FXML
    private TextField addr;

    @FXML
    private void returnToHome(){
        Main.getMainScene().setRoot(new HomeComponent());
    }

    @FXML
    private void createAccount() {
        String mail = this.mail.getText();
        String firstname = this.firstname.getText();
        String lastname = this.lastname.getText();
        String addr = this.addr.getText();
        if(mail.matches(".+@.+")){
            User user = new User(mail, firstname, lastname, addr);
            if(Requests.pushNewUser(user)) {
                this.getScene().setRoot(new HomeComponent());
            }
            else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erreur");
                alert.setHeaderText("L'insertion a échoué");
                alert.setContentText("L'utilisateur existe déjà");
                alert.showAndWait();
            }
        }
        else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText("Email Invalide");
            alert.setContentText("Entrez un email valide");
            alert.showAndWait();
        }
    }

    public AccountCreationComponent() {
        super();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/newAccount.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }
}
