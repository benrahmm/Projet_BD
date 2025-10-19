package com.belec.app;

import com.belec.app.data.User;
import com.belec.app.gui.HomeComponent;
import com.belec.app.sql.Requests;
import com.belec.app.sql.SQLRequests;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class Main extends Application {

    private static Connection dbConnection = null;
    private static User mainUser = null;
    private static Scene mainScene;

    private static Timer timer;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        try {
            dbConnection = DriverManager.getConnection(
                    SQLRequests.SQL_SERVER_LOCATION, SQLRequests.SQL_USER, SQLRequests.SQL_PASSWORD);
            dbConnection.setAutoCommit(false);
            dbConnection.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            public void run() {
                if(Main.getMainUser() != null)
                    Requests.decreaseSale(Main.getMainUser().getMail());
            }}, 0, 60*1000);
        primaryStage.setTitle("Baie-électronique");
        mainScene = new Scene(new HomeComponent());
        primaryStage.setScene(mainScene);
        primaryStage.show();
    }

    @Override
    public void stop() throws Exception {
        super.stop();
        timer.cancel();
        timer.purge();
        dbConnection.close();
    }

    public static Connection getDbConnection() {
        return dbConnection;
    }

    public static void setMainUser(User user) {
        mainUser = user;
    }

    public static User getMainUser() {
        return mainUser;
    }

    public static Scene getMainScene() {
        return mainScene;
    }

    public static Timer getTimer() {
        return timer;
    }
}
