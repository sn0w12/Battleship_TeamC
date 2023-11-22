package com.example.battleship_teamc;

import ships.Ship;
import java.util.Random;

public class Logic {

    private final Board playerBoard;

    public Logic(Board playerBoard, Fleet playerFleet) {
        // Konstruktorn för Logic-klassen tar in en spelplan samt två flotte.
        this.playerBoard = playerBoard;
    }

    public boolean placeShips(Board board, Fleet fleet) {
        Random random = new Random();
        int maxAttempts = 100; // Begränsa antalet försök för att undvika oändliga loopar
        boolean allShipsPlacedSuccessfully = true; //Lagt till en kontroll så att alla skepp placeras annars försöker den igen - Johanna

        // För varje skepp i flottan
        for (Ship ship : fleet.getPlayerFleet()) {
            char orientation = random.nextBoolean() ? 'H' : 'V'; // Slumpmässig orientering
            int attempts = 0;

            while (attempts < maxAttempts) {
                int row, col;

                // Slumpmässig placering av skeppet på spelplanen
                if (orientation == 'H') {
                    row = random.nextInt(board.getRows());
                    col = random.nextInt(board.getCols() - ship.getSize() + 1);
                } else {
                    row = random.nextInt(board.getRows() - ship.getSize() + 1);
                    col = random.nextInt(board.getCols());
                }

                // Kontrollera om placeringen är giltig och inte kolliderar med andra skepp
                boolean isValid = true;

                if (orientation == 'H') {
                    isValid = isValid(board, row, col, isValid, 1, ship.getSize(), ship);
                } else {
                    isValid = isValid(board, row, col, isValid, ship.getSize(), 1, ship);
                }

                if (isValid) {
                    placeShipOnBoard(board, ship.getSize(), row, col, orientation);
                    ship.setPlaced(true); // Markera skeppet som placerat
                    break;
                }
                attempts++;
            }
            if (attempts == maxAttempts) {
                allShipsPlacedSuccessfully = false;
            }
        }
        // Skriv ut spelplanen efter att skeppen är placerade
        board.printBoard();
        return allShipsPlacedSuccessfully;
    }

    private boolean isValid(Board board, int row, int col, boolean isValid, int i2, int size, Ship ship) {
        for (int i = row - 1; i <= row + i2; i++) {
            for (int j = col - 1; j <= col + size; j++) {
                if (i >= 0 && i < board.getRows() && j >= 0 && j < board.getCols() && board.hasShip(i, j)) {
                    isValid = false;
                    break;
                }
            }
        }
        return isValid;
    }

    private void placeShipOnBoard(Board board, int size, int row, int col, char orientation) {
        if (orientation == 'H') {
            for (int i = 0; i < size; i++) {
                board.placeShip(row, col + i);
            }
        } else if (orientation == 'V') {
            for (int i = 0; i < size; i++) {
                board.placeShip(row + i, col);
            }
        }
    }
}