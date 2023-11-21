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
import javafx.scene.control.TextField;
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
    @FXML
    private TextField serverTextField;
    @FXML
    private TextField clientTextField;
    private boolean isServer;

    private final Board board;
    private final Logic logic;
    private static final int GRID_SIZE = 10;
    private final Fleet fleet;
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
        this.board = new Board(GRID_SIZE, GRID_SIZE);
        this.fleet = new Fleet();
        this.logic = new Logic(board, fleet);
    }

    public void placeShipsRandomly(GridPane grid, Board gameBoard, Fleet playerFleet, Logic gameLogic) {
        gameBoard.clearBoard();
        playerFleet.resetFleet();
        gameLogic.placeShips(gameBoard, playerFleet);
        updateGridPaneFromBoard(grid, gameBoard);
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
            placeShipsRandomly(serverGrid, board, fleet, logic);
        } else {
            clientGrid.getChildren().remove(1, clientGrid.getChildren().size());
            placeShipsRandomly(clientGrid, board, fleet, logic);
        }
    }

    @FXML
    public void showIfClientOrServer() {
        if (isServer) {
            serverTextField.setStyle("-fx-control-inner-background: #96ea96;");
        } else {
            clientTextField.setStyle("-fx-control-inner-background: #96ea96;");
        }
    }

    @FXML
    public void handleStartGameButton() {
        Thread connectionThread = new Thread(() -> {
            if (!isServer()) {
                Client client = new Client("localhost", 8080);
                client.connectToServer();
            } else {
                Server server = new Server(8080);
                server.start();
            }
        });
        connectionThread.start();
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


