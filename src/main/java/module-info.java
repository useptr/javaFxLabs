module com.example.fx2 {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.fx2 to javafx.fxml;
    exports com.example.fx2;
    exports com.example.fx2.MainScreen;
    opens com.example.fx2.MainScreen to javafx.fxml;
}