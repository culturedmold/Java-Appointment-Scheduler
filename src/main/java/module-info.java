module com.example.c195_software2 {
    requires javafx.controls;
    requires javafx.fxml;
//    requires mysql.connector.j;

    requires org.kordamp.bootstrapfx.core;
    requires java.sql;

    opens com.example.c195_software2 to javafx.fxml, javafx.base;
    exports com.example.c195_software2;
    exports com.example.c195_software2.controller;
    opens com.example.c195_software2.controller to javafx.fxml;
    exports com.example.c195_software2.database;
    opens com.example.c195_software2.database to javafx.fxml;
//    opens com.example.c195_software2.model to javafx.base;
}