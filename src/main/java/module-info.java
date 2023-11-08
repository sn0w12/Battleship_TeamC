module com.example.battleship_teamc {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.example.battleship_teamc to javafx.fxml;
    exports com.example.battleship_teamc;
}