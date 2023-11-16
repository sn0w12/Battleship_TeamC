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
    private GridPane clientGrid;

    @FXML
    private GridPane serverGrid;
    private GridPane tempGrid;
    private boolean isServer;
    
    private final Board board;
    private final Logic logic;
    private static final int GRID_SIZE = 10;
    private final Fleet fleet;

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

    public void placeShipsOnMap() {
        if (isServer){
            serverGrid.getChildren().remove(1, serverGrid.getChildren().size());
            placeShipsRandomly(serverGrid, board, fleet, logic);
        } else {
            clientGrid.getChildren().remove(1, clientGrid.getChildren().size());
            placeShipsRandomly(clientGrid, board, fleet, logic);
        }
    }
}