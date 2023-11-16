package com.example.battleship_teamc;
import javafx.event.Event;
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

public class GameController {
    @FXML
    Button mainMenuButton;
    @FXML
    private GridPane clientGrid;
    @FXML
    private GridPane serverGrid;
    private boolean isServer;

    public boolean isServer() {
        return isServer;
    }
    public void setServer(boolean server) {
        isServer = server;
    }

    private static final int GRID_SIZE = 10;
    private int maxAttempts = 100;
    private int count = 0;


    public void placeShipsRandomly(GridPane gridPane, List<Ship> fleet) {
        Random random = new Random();
        System.out.println("Försök nr " + ++count);

        for (Ship ship : fleet) {
            int size = ship.getSize();
            boolean horizontal = random.nextBoolean();

            int col;
            int row;
            int attempts = 0;

            boolean placedSuccessfully = false;

            while (!placedSuccessfully && attempts < maxAttempts) {
                if (horizontal) {
                    col = (int) (Math.random() * (GRID_SIZE - size + 1));
                    row = random.nextInt(GRID_SIZE);
                } else {
                    col = random.nextInt(GRID_SIZE);
                    row = (int) (Math.random() * (GRID_SIZE - size + 1));
                }

                if (isPlacementValid(gridPane, col, row, size, horizontal)) {
                    for (int i = 0; i < size; i++) {
                        Rectangle rectangle = new Rectangle(20, 20, Color.BLUE);
                        gridPane.add(rectangle, col + (horizontal ? i : 0), row + (horizontal ? 0 : i));
                    }
                    placedSuccessfully = true;
                }

                attempts++;
            }

            if (!placedSuccessfully) {
                // Om skeppen ej kan placeras ut inom maxförsöken, starta om processen för hela flottan
                System.out.println("Kunde ej placera ut skepp: " + ship.getName() + " - Startar om placeringen för hela flottan");
                gridPane.getChildren().remove(1, gridPane.getChildren().size());
                placeShipsRandomly(gridPane, fleet);
                break; // Avsluta loopen eftersom vi startar om processen
            }
        }
    }

    private boolean isPlacementValid(GridPane gridPane, int col, int row, int size, boolean horizontal) {
        // Kontrollera närliggande celler inkl diagonala
        for (int i = -1; i <= size; i++) {
            for (int j = -1; j <= size; j++) {
                int currentCol = col + (horizontal ? i : 0);
                int currentRow = row + (horizontal ? 0 : j);

                if (currentCol >= 0 && currentCol < GRID_SIZE && currentRow >= 0 && currentRow < GRID_SIZE) {
                    // Kontrollera om cellen och närliggande celler är upptagna
                    for (Node node : gridPane.getChildren()) {
                        Integer nodeCol = GridPane.getColumnIndex(node);
                        Integer nodeRow = GridPane.getRowIndex(node);

                        if (nodeCol != null && nodeRow != null &&
                                (nodeCol >= currentCol - 1 && nodeCol <= currentCol + 1) &&
                                (nodeRow >= currentRow - 1 && nodeRow <= currentRow + 1)) {
                            return false; // Skepp kan ej placeras här; cell eller närliggande cell är upptagen.
                        }
                    }
                }
            }
        }
        return true;
    }

    @FXML
    private void handleMainMenuButton(Event event) throws IOException {
        Parent fxmlLoader = FXMLLoader.load(HelloApplication.class.getResource("Hello-View.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(fxmlLoader);
        stage.setScene(scene);
    }

    public void placeShipsOnMap() {

        Fleet fleet = new Fleet();
        if (isServer){
            serverGrid.getChildren().remove(1, serverGrid.getChildren().size());
            placeShipsRandomly(serverGrid, fleet.getPlayerFleet());
        } else {
            clientGrid.getChildren().remove(1, clientGrid.getChildren().size());
            placeShipsRandomly(clientGrid, fleet.getPlayerFleet());
        }
    }
}

