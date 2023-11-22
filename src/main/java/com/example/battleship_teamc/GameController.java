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
import javafx.application.Platform;

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
    @FXML
    private Slider shotDelaySlider;
    @FXML
    private Label shotDelayLabel;
    @FXML
    private Label winnerLabel;
    private String winner;
    private boolean isServer;
    private final Board userBoard;
    private final Board tempBoard;
    private final Logic userLogic;
    private static final int GRID_SIZE = 10;
    private final Fleet userFleet;
    public Button startGameButton;
    private int shotDelay;

    public boolean isServer() {
        return isServer;}
    public void setServer(boolean server) {
        isServer = server;}
    public Label getWinnerLabel() {
        return winnerLabel;}
    public void setWinnerLabel(Label winnerLabel) {
        this.winnerLabel = winnerLabel;}
    public String getWinner() {
        return winner;}
    public void setWinner(String winner) {
        this.winner = winner;}

    public GameController() {
        this.userBoard = new Board(GRID_SIZE, GRID_SIZE);
        this.userFleet = new Fleet();
        this.userLogic = new Logic(userBoard, userFleet);
        this.tempBoard = new Board(GRID_SIZE, GRID_SIZE);
    }

    public void placeShipsRandomly(GridPane grid, Board gameBoard, Fleet playerFleet, Logic gameLogic) {
        boolean placementSuccess; //Lagt till en kontroll så att alla skepp placeras annars försöker den igen - Johanna

        do {
            gameBoard.clearBoard();
            playerFleet.resetFleet();
            placementSuccess = gameLogic.placeShips(gameBoard, playerFleet);
        } while (!placementSuccess);
            updateBoard(grid, gameBoard);
    }

    void updateBoard(GridPane grid, Board gameBoard) {
        new Thread(() -> {
            Platform.runLater(() -> {
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
            });
        }).start();
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
            placeShipsRandomly(serverGrid, userBoard, userFleet, userLogic);
            updateBoard(clientGrid, tempBoard);
        } else {
            clientGrid.getChildren().remove(1, clientGrid.getChildren().size());
            placeShipsRandomly(clientGrid, userBoard, userFleet, userLogic);
            updateBoard(serverGrid, tempBoard);
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
        // Server thread
        Thread serverThread = new Thread(() -> {
            Server server = new Server(8080, userBoard, tempBoard, this, serverGrid, clientGrid, shotDelay);
            try {
                server.start();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        // Client thread
        Thread clientThread = new Thread(() -> {
            Client client = new Client("localhost", 8080, this, userBoard, tempBoard, serverGrid, clientGrid, shotDelay);
            try {
                client.start();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            // Additional client-side logic to wait for and handle the server's "Game Start" message
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
        Platform.runLater(() -> winnerLabel.setText(""));
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