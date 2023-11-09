
package com.example.battleship_teamc;

import ships.*;
import java.util.Random;

public class Logic {
    private final Board playerBoard;
    private final Board opponentBoard;

    public Logic(Board playerBoard, Board opponentBoard, Fleet playerFleet, Fleet opponentFleet) {
        // Konstruktorn för Logic-klassen tar in två spelplaner (för spelare och motståndare) samt två flottor (för spelare och motståndare).
        this.playerBoard = playerBoard;
        this.opponentBoard = opponentBoard;

        // Metoden placeShips() anropas för att placera skepp slumpmässigt på båda spelplanerna.
        placeShips(playerBoard, playerFleet);
        placeShips(opponentBoard, opponentFleet);
    }

    private void placeShips(Board board, Fleet fleet) {
        Random random = new Random();
        int maxAttempts = 100; // Begränsa antalet försök för att undvika oändliga loopar

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
        }

        // Skriv ut spelplanen efter att skeppen är placerade
        board.printBoard();
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

    /*private boolean isValidPlacement(Board board, int size, int row, int col, char orientation) {
        if (orientation == 'H') {
            if (col + size > board.getCols()) {
                return false; // Placeringen går utanför spelplanen horisontellt
            }

            for (int i = 0; i < size; i++) {
                if (board.hasShip(row, col + i)) {
                    return false; // En annan båt finns redan i närheten
                }
            }
        } else if (orientation == 'V') {
            if (row + size > board.getRows()) {
                return false; // Placeringen går utanför spelplanen vertikalt
            }

            for (int i = 0; i < size; i++) {
                if (board.hasShip(row + i, col)) {
                    return false; // En annan båt finns redan i närheten
                }
            }
        }

        return true; // Placeringen är giltig
    }*/

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

    // Metod för att kontrollera om ett skott är en träff
    boolean isHit(Board board, int row, int col) {
        // Miss
        return board.getCell(row, col) == 'S'; // Träff
    }

    // Metod för att utföra ett skott
    private void shoot(int player, int row, int col) {
        Board targetBoard = (player == 1) ? opponentBoard : playerBoard;

        if (row >= 0 && row < targetBoard.getRows() && col >= 0 && col < targetBoard.getCols()) {
            if (!targetBoard.hasBeenFired(row, col)) {
                targetBoard.shoot(row, col);

                char result = targetBoard.getCell(row, col);
                if (result == 'X') {
                    if (targetBoard.isAllShipsSunk()) {
                        System.out.println("Spelare " + player + " sänkte ett skepp!");
                    } else {
                        System.out.println("Spelare " + player + " träffade!");
                    }
                } else {
                    System.out.println("Spelare " + player + " missade!");
                }
            }
            playerBoard.printBoard();

            opponentBoard.printBoard();
        }
    }

    // Ny metod som kombinerar random skott och skottutförande
    public String randomShotAndShoot(int player) {
        Random random = new Random();
        int row, col;

        String shotResult; // Declare the variable here

        do {
            row = random.nextInt(opponentBoard.getRows());
            col = random.nextInt(opponentBoard.getCols());
        } while (opponentBoard.getCell(row, col) != '.' && opponentBoard.getCell(row, col) != 'S'); // Continue if cell is not '.' or 'S'

        if (isHit(opponentBoard, row, col)) {
            opponentBoard.shoot(row, col);
            shotResult = Protocol.HIT;

            if (opponentBoard.isAllShipsSunk()) {
                shotResult = Protocol.SUNK;
                System.out.println("Sänkt! Spelare " + player + " sänkte motståndarens skepp på cell " + row + col);
            } else {
                System.out.println("Träff! Spelare " + player + " träffade motståndaren på cell " + row + col);
            }
        } else {
            opponentBoard.shoot(row, col);
            shotResult = Protocol.MISS;
            System.out.println("Miss! Spelare " + player + " missade motståndaren på cell " + row + col);
        }

        // Skriv ut spelplanerna efter skottet
        playerBoard.printBoard();
        opponentBoard.printBoard();

        return shotResult;
    }

    // Helper method to check if any ships are left on a board
    private boolean areShipsLeft(Board board) {
        for (int row = 0; row < board.getRows(); row++) {
            for (int col = 0; col < board.getCols(); col++) {
                if (board.getCell(row, col) == 'S') {
                    return true; // Ships are still present
                }
            }
        }
        return false; // No ships left
    }

    // Method to check if the game is finished
    public boolean isGameFinished() {
        // Check both boards for any remaining ships
        return areShipsLeft(playerBoard) && areShipsLeft(opponentBoard);
    }

    public String getCurrentPlayer() {

        return null;
    }

    public Object getCurrentResult() {
        return null;
    }
}
