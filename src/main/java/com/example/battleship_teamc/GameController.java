package com.example.battleship_teamc;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import ships.Ship;

import java.util.List;
import java.util.Random;

public class GameController {

    @FXML
    private GridPane ClientGrid;

    private static final int GRID_SIZE = 10;
    private int maxAttempts = 1000;
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
                // If the ship cannot be placed within maxAttempts, restart the placement process for the entire fleet
                System.out.println("Kunde ej placera ut skepp: " + ship.getName() + " - Startar om placeringen för hela flottan");
                ClientGrid.getChildren().remove(1, ClientGrid.getChildren().size());
                placeShipsRandomly(gridPane, fleet);
                break; // Exit the loop since we're restarting the placement process
            }
        }
    }

    private boolean isPlacementValid(GridPane gridPane, int col, int row, int size, boolean horizontal) {
        // Check the surrounding cells including diagonals
        for (int i = -1; i <= size; i++) {
            for (int j = -1; j <= size; j++) {
                int currentCol = col + (horizontal ? i : 0);
                int currentRow = row + (horizontal ? 0 : j);

                if (currentCol >= 0 && currentCol < GRID_SIZE && currentRow >= 0 && currentRow < GRID_SIZE) {
                    // Check if the cell or its adjacent cells are already occupied
                    for (Node node : gridPane.getChildren()) {
                        Integer nodeCol = GridPane.getColumnIndex(node);
                        Integer nodeRow = GridPane.getRowIndex(node);

                        if (nodeCol != null && nodeRow != null &&
                                (nodeCol >= currentCol - 1 && nodeCol <= currentCol + 1) &&
                                (nodeRow >= currentRow - 1 && nodeRow <= currentRow + 1)) {
                            return false; // Ship cannot be placed here; cell or adjacent cell is already occupied.
                        }
                    }
                }
            }
        }

        return true;
    }

    public void initialize() {
        // This method is called after the FXML file has been loaded.
        ClientGrid.getChildren().remove(1, ClientGrid.getChildren().size());
        Fleet fleet = new Fleet();
        placeShipsRandomly(ClientGrid, fleet.getPlayerFleet());
    }
}

