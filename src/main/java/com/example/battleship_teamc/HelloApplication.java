package com.example.battleship_teamc;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {

    @FXML
    private GridPane clientGrid;
    @FXML
    private GridPane serverGrid;


    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Scene firstScene = new Scene(fxmlLoader.load());
        stage.setTitle("Sänka Skepp");
        stage.setScene(firstScene);
        stage.show();

        Button playAsClient = (Button) firstScene.lookup("#playAsClient");
        Button playAsServer = (Button) firstScene.lookup("#playAsServer");

        playAsClient.setOnAction(event -> {
            try {
                FXMLLoader clientLoader = new FXMLLoader(HelloApplication.class.getResource("GameView.fxml"));
                Scene clientScene = new Scene(clientLoader.load());
                stage.setScene(clientScene);

                GameController controller = clientLoader.getController();
                controller.setServer(false);
                controller.placeShipsOnMap();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        playAsServer.setOnAction(event -> {
            try {
                FXMLLoader serverLoader = new FXMLLoader(HelloApplication.class.getResource("GameView.fxml"));
                Scene serverScene = new Scene(serverLoader.load());
                stage.setScene(serverScene);

                GameController controller = serverLoader.getController();
                controller.setServer(true);
                controller.placeShipsOnMap();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
    public static void main (String[]args){
        launch();
    }

}