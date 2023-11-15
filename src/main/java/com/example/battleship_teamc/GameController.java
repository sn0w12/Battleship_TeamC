package com.example.battleship_teamc;

import javafx.fxml.FXML;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import ships.Ship;

import java.util.List;
import java.util.Random;

public class GameController {

    @FXML
    private GridPane ClientGrid;
    private final Board gameBoard;
    private final Logic gameLogic;
    private static final int GRID_SIZE = 10;
    private final Fleet playerFleet;

    public GameController() {
        this.gameBoard = new Board(GRID_SIZE, GRID_SIZE); // Assuming a 10x10 board
        this.playerFleet = new Fleet(); // Initialize player fleet
        this.gameLogic = new Logic(gameBoard, playerFleet);
    }

    public void placeShipsRandomly(Board gameBoard, Fleet playerFleet) {
        gameBoard.clearBoard();
        playerFleet.resetFleet();
        gameLogic.placeShips(gameBoard, playerFleet);
        updateGridPaneFromBoard();
    }

    private void updateGridPaneFromBoard() {
        ClientGrid.getChildren().clear();
        for (int row = 0; row < GRID_SIZE; row++) {
            for (int col = 0; col < GRID_SIZE; col++) {
                Rectangle rectangle = new Rectangle(20, 20);
                char cell = gameBoard.getCell(row, col);

                if (cell == 'S') {
                    rectangle.setFill(Color.GRAY); // Ship
                } else if (cell == 'X') {
                    rectangle.setFill(Color.RED); // Hit
                } else if (cell == 'O') {
                    rectangle.setFill(Color.BLACK); // Miss
                } else {
                    rectangle.setFill(Color.BLUE); // Water
                }

                ClientGrid.add(rectangle, col, row);
            }
        }
    }

    public void initialize() {
        // This method is called after the FXML file has been loaded.
        ClientGrid.getChildren().remove(1, ClientGrid.getChildren().size());
        Fleet fleet = new Fleet();
        placeShipsRandomly(gameBoard, playerFleet);
    }
}