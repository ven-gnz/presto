module com.example.presto {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;


    opens com.example.presto to javafx.fxml;
    exports com.example.presto.Editor;
    opens com.example.presto.Editor to javafx.fxml;
}