package com.example.battleship_teamc;
import javafx.fxml.FXML;
import javafx.scene.Node;
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

    public void placeShipsRandomly(GridPane gridPane, List<Ship> fleet) {
        Random random = new Random();

        for (Ship ship : fleet) {
            int size = ship.getSize();
            boolean horizontal = random.nextBoolean();

            int col;
            int row;

            do {
                col = random.nextInt(GRID_SIZE - (horizontal ? size : 1));
                row = random.nextInt(GRID_SIZE - (horizontal ? 1 : size));
            } while (!isPlacementValid(gridPane, col, row, size, horizontal));

            for (int i = 0; i < size; i++) {
                Rectangle rectangle = new Rectangle(20, 20, Color.BLUE);
                gridPane.add(rectangle, col + (horizontal ? i : 0), row + (horizontal ? 0 : i));
            }
        }
    }

    private boolean isPlacementValid(GridPane gridPane, int col, int row, int size, boolean horizontal) {
        for (int i = 0; i < size; i++) {
            int currentCol = col + (horizontal ? i : 0);
            int currentRow = row + (horizontal ? 0 : i);

            if (currentCol < 0 || currentCol >= GRID_SIZE || currentRow < 0 || currentRow >= GRID_SIZE) {
                return false;
            }

            // Check if the cell is already occupied
            for (Node node : gridPane.getChildren()) {
                Integer nodeCol = GridPane.getColumnIndex(node);
                Integer nodeRow = GridPane.getRowIndex(node);

                if (nodeCol != null && nodeRow != null && nodeCol == currentCol && nodeRow == currentRow) {
                    return false; // Ship cannot be placed here; cell is already occupied.
                }
            }
        }

        return true;
    }
    public void initialize() {
        // This method is called after the FXML file has been loaded.

        Fleet fleet = new Fleet();
        placeShipsRandomly(ClientGrid, fleet.getPlayerFleet());
    }
}

