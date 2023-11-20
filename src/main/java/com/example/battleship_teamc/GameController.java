package com.example.battleship_teamc;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import ships.Ship;
import java.io.IOException;
import java.util.List;
import java.util.Random;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;

public class GameController {
    @FXML
    Button mainMenuButton;
    @FXML
    private GridPane clientGrid;
    @FXML
    private GridPane serverGrid;
    private boolean isServer;
    
    private final Board clientBoard;
    private final Board serverBoard;
    private final Logic clientLogic;
    private final Logic serverLogic;
    private static final int GRID_SIZE = 10;
    private final Fleet clientFleet;
    private final Fleet serverFleet;

    public Button startGameButton;

    @FXML
    private Slider shotDelaySlider;

    @FXML
    private Label shotDelayLabel;

    private int shotDelay;

    public boolean isServer() {
        return isServer;
    }

    public void setServer(boolean server) {
        isServer = server;
    }

    public GridPane getClientGrid() {
        return clientGrid;
    }

    public GridPane getServerGrid() {
        return serverGrid;
    }

    public GameController() {
        this.clientBoard = new Board(GRID_SIZE, GRID_SIZE);
        this.clientFleet = new Fleet();
        this.clientLogic = new Logic(clientBoard, clientFleet);

        this.serverBoard = new Board(GRID_SIZE, GRID_SIZE);
        this.serverFleet = new Fleet();
        this.serverLogic = new Logic(serverBoard, serverFleet);
    }

    public void placeShipsRandomly(GridPane grid, Board gameBoard, Fleet playerFleet, Logic gameLogic, boolean isEnemy) {
        gameBoard.clearBoard();
        playerFleet.resetFleet();
        gameLogic.placeShips(gameBoard, playerFleet);
        if (!isEnemy)
            updateGridPaneFromBoard(grid, gameBoard);
        else
            updateEnemyBoard(grid, gameBoard);
    }

    private void updateGridPaneFromBoard(GridPane grid, Board gameBoard) {
        grid.getChildren().clear();
        for (int row = 0; row < GRID_SIZE; row++) {
            for (int col = 0; col < GRID_SIZE; col++) {
                Rectangle rectangle = new Rectangle(20, 20);
                char cell = gameBoard.getCell(row, col);

                switch (cell) {
                    case 'S' -> rectangle.setFill(Color.GRAY); // Ship
                    case 'X' -> rectangle.setFill(Color.RED); // Hit
                    case 'O' -> rectangle.setFill(Color.BLACK); // Miss
                    default -> rectangle.setFill(Color.BLUE); // Water
                }

                grid.add(rectangle, col, row);
            }
        }
    }

    private void updateEnemyBoard(GridPane grid, Board gameBoard) {
        grid.getChildren().clear();
        for (int row = 0; row < GRID_SIZE; row++) {
            for (int col = 0; col < GRID_SIZE; col++) {
                Rectangle rectangle = new Rectangle(20, 20);
                char cell = gameBoard.getCell(row, col);

                switch (cell) {
                    case 'X' -> rectangle.setFill(Color.RED); // Hit
                    case 'O' -> rectangle.setFill(Color.BLACK); // Miss
                    default -> rectangle.setFill(Color.BLUE); // Water
                }

                grid.add(rectangle, col, row);
            }
        }
    }

    @FXML
    private void handleMainMenuButton(Event event) throws IOException {
        Parent fxmlLoader = FXMLLoader.load(HelloApplication.class.getResource("Hello-View.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(fxmlLoader);
        stage.setScene(scene);
    }

    public void placeShipsOnMap() {
        if (isServer) {
            serverGrid.getChildren().remove(1, serverGrid.getChildren().size());
            placeShipsRandomly(serverGrid, serverBoard, serverFleet, serverLogic, false);
            placeShipsRandomly(clientGrid, clientBoard, clientFleet, clientLogic, true);
        } else {
            clientGrid.getChildren().remove(1, clientGrid.getChildren().size());
            placeShipsRandomly(clientGrid, clientBoard, clientFleet, clientLogic, false);
            placeShipsRandomly(serverGrid, serverBoard, serverFleet, serverLogic, true);
        }
    }

    @FXML
    public void handleStartGameButton() {
        // Server thread
        Thread serverThread = new Thread(() -> {
            Server server = new Server(8080);
            server.start();
        });

        // Client thread
        Thread clientThread = new Thread(() -> {
            // Ensure the server has some time to start up
            try {
                Thread.sleep(1000); // wait for 1 second before starting the client
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            Client client = new Client("localhost", 8080);
            client.connectToServer();
        });

        // Start both threads
        if (!isServer()) {
            clientThread.start();
        } else {
            serverThread.start();
        }
    }

    public void initialize() {
        Thread sliderThread = new Thread(() -> {
            customizeShotDelay();
        });
        sliderThread.start();
    }

    public void customizeShotDelay() {
        shotDelaySlider.setMin(0);
        shotDelaySlider.setMax(5000); // 5000 ms (5 sekunder)
        shotDelaySlider.setValue(2500); // Standardvärde, (2500 ms eller 2.5 sekunder)
        shotDelaySlider.setShowTickMarks(true);

        shotDelaySlider.valueProperty().addListener((observable, oldValue, newValue) -> {
            shotDelay = newValue.intValue();
            double seconds = newValue.intValue() / 1000.0; // Konvertera till sekunder
            shotDelayLabel.setText("Shot Delay: " + seconds + " s"); // Uppdatera label med fördröjningen i sekunder
        });
    }

}
    // Denna ska läggas in i skottmetoden. Fördröjning innan nästa skott kan avfyras baserat på shotDelay
    /*
    int delay = shotDelay;
    try {
        Thread.sleep(shotDelay); // shotDelay innehåller värdet från slidern
    } catch (InterruptedException e) {
        e.printStackTrace();*/


