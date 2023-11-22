package com.example.battleship_teamc;

import javafx.event.ActionEvent;
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
    private void handlePlayAsClientButton(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = loadGameView(event);
        GameController controller = fxmlLoader.getController();
        controller.setServer(false);
        controller.placeShipsOnMap();
        controller.showIfClientOrServer();
    }

    @FXML
    private void handlePlayAsServerButton(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = loadGameView(event);
        GameController controller = fxmlLoader.getController();
        controller.setServer(true);
        controller.placeShipsOnMap();
        controller.showIfClientOrServer();
    }

    private FXMLLoader loadGameView(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("GameView.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(fxmlLoader.load(), 975, 400);
        stage.setScene(scene);
        stage.setResizable(false);
        return fxmlLoader;
    }
}