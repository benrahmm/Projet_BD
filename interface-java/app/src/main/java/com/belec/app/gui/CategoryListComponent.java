package com.belec.app.gui;

import com.belec.app.Main;
import com.belec.app.data.Category;
import com.belec.app.sql.Requests;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.util.Collection;

public class CategoryListComponent extends Pane {

    private Collection<Category> categories;

    @FXML
    private void returnButton() {
        Main.getMainScene().setRoot(new HomeComponent());
    }

    public CategoryListComponent() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/categories.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            exception.printStackTrace();
        }
        categories = Requests.retrievesAllCategories();
        VBox box = (VBox) this.getChildren().get(1);
        box.getChildren().clear();
        if(categories != null) {
            for(Category category : categories) {
                Button button = new Button(category.getName());
                button.setMaxWidth(Double.MAX_VALUE);
                button.setMaxHeight(Double.MAX_VALUE);
                button.setOnAction(event -> {
                    Main.getMainScene().setRoot(new SalesRoomListComponent(category));
                });
                box.getChildren().add(button);
            }
        }
    }
}
