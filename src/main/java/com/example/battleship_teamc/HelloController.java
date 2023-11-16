package com.example.battleship_teamc;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloController {

    public Button playAsServer;
    public Button playAsClient;

    @FXML
    private void handlePlayAsClientButton(Event event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("GameView.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);

        GameController controller = fxmlLoader.getController();
        controller.setServer(false);
        controller.placeShipsOnMap();
    }

    @FXML
    private void handlePlayAsServerButton(Event event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("GameView.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);

        GameController controller = fxmlLoader.getController();
        controller.setServer(true);
        controller.placeShipsOnMap();
    }
}
