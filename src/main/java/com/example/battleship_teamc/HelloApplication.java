package com.example.battleship_teamc;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Scene firstScene = new Scene(fxmlLoader.load());
        stage.setTitle("Sänka Skepp");
        stage.setScene(firstScene);
        stage.show();
    }
}