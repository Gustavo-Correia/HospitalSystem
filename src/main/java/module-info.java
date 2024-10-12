module com.hospital2 {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.hospital2 to javafx.fxml;
    exports com.hospital2;
}
