module com.belec.app {
    requires javafx.base;
    requires javafx.controls;
    requires javafx.graphics;
    requires javafx.fxml;
    requires java.sql;
    requires java.desktop;
    exports com.belec.app to javafx.graphics;
    opens com.belec.app to javafx.fxml;
    exports com.belec.app.gui to javafx.graphics;
    opens com.belec.app.gui to javafx.fxml;
    exports com.belec.app.sql to javafx.graphics;
    opens com.belec.app.sql to javafx.fxml;
}