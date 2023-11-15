package com.example.battleship_teamc;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Scene firstScene = new Scene(fxmlLoader.load());
        stage.setTitle("Sänka Skepp");
        stage.setScene(firstScene);
        stage.show();

        Button startClient = (Button) firstScene.lookup("#playAsClient");
        Button startServer = (Button) firstScene.lookup("#playAsServer");

        startClient.setOnAction(event -> {
            try {
                FXMLLoader clientLoader = new FXMLLoader(HelloApplication.class.getResource("GameView.fxml"));
                Scene clientScene = new Scene(clientLoader.load());
                stage.setScene(clientScene);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        startServer.setOnAction(event -> {
            try {
                FXMLLoader serverLoader = new FXMLLoader(HelloApplication.class.getResource("GameView.fxml"));
                Scene serverScene = new Scene(serverLoader.load());
                stage.setScene(serverScene);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
    public static void main (String[]args){
        launch();
    }
}