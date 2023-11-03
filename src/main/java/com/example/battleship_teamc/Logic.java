package com.example.battleship_teamc;

import java.util.Random;
import ships.*;

public class Logic {
    private Board playerBoard = new Board(10, 10);
    private Board opponentBoard = new Board(10, 10);
    private Fleet playerFleet;
    private Fleet opponentFleet;
    private boolean isGameOver;

    public Logic(Board playerBoard, Board opponentBoard, Fleet playerFleet, Fleet opponentFleet) {
        // Konstruktorn för Logic-klassen tar in två spelplaner (för spelare och motståndare) samt två flottor (för spelare och motståndare).
        this.playerBoard = playerBoard;
        this.opponentBoard = opponentBoard;
        this.playerFleet = playerFleet;
        this.opponentFleet = opponentFleet;
        this.isGameOver = false;

        // Metoden placeShips() anropas för att placera skepp slumpmässigt på båda spelplanerna.
        placeShips(playerBoard, playerFleet);
        placeShips(opponentBoard, opponentFleet);
    }

    private void placeShips(Board board, Fleet fleet) {
        Random random = new Random();

        // För varje skepp i flottan
        for (Ship ship : fleet.getPlayerFleet()) {
            char orientation = random.nextBoolean() ? 'H' : 'V'; // Slumpmässig orientering

            while (true) {
                int row, col;

                // Slumpmässig placering av skeppet på spelplanen
                if (orientation == 'H') {
                    row = random.nextInt(board.getRows());
                    col = random.nextInt(board.getCols() - ship.getSize() + 1);
                } else {
                    row = random.nextInt(board.getRows() - ship.getSize() + 1);
                    col = random.nextInt(board.getCols());
                }

                // Kontrollera om placeringen är giltig
                if (board.isSpaceAvailable(row, col, ship, orientation)) {
                    board.addShip(row, col, ship, orientation);
                    ship.setPlaced(true); // Markera skeppet som placerat
                    break;
                }
            }
        }

        // Skriv ut spelplanen efter att skeppen är placerade
        board.printBoard();
    }

    /*
    private boolean isValidPlacement(Board board, int size, int row, int col, char orientation) {
        if (orientation == 'H') {
            if (col + size > board.getCols()) {
                return false; // Placeringen går utanför spelplanen horisontellt
            }

            for (int i = -1; i <= size; i++) {
                for (int j = -1; j <= 1; j++) {
                    int newRow = row + i;
                    int newCol = col + j;

                    if (newRow >= 0 && newRow < board.getRows() && newCol >= 0 && newCol < board.getCols()) {
                        if (board.hasShip(newRow, newCol)) {
                            return false; // En annan båt finns redan i närheten
                        }
                    }
                }
            }
        } else if (orientation == 'V') {
            if (row + size > board.getRows()) {
                return false; // Placeringen går utanför spelplanen vertikalt
            }

            for (int i = -1; i <= 1; i++) {
                for (int j = -1; j <= size; j++) {
                    int newRow = row + i;
                    int newCol = col + j;

                    if (newRow >= 0 && newRow < board.getRows() && newCol >= 0 && newCol < board.getCols()) {
                        if (board.hasShip(newRow, newCol)) {
                            return false; // En annan båt finns redan i närheten
                        }
                    }
                }
            }
        }

        return true; // Placeringen är giltig
    }
    */

    private void placeShipOnBoard(Board board, int size, int row, int col, char orientation, Ship ship) {
        board.addShip(row, col, ship, orientation);
    }

    void randomShot() {
        Random random = new Random();
        int row, col;

        do {
            row = random.nextInt(opponentBoard.getRows());
            col = random.nextInt(opponentBoard.getCols());
        } while (opponentBoard.hasBeenFired(row, col));

        // Skjutmetoden med en slumpmässig fördröjning inte den som ska vara till slider. Bara ett exempel
        int shotDelay = random.nextInt(5000); // Slumpmässig fördröjning i millisekunder (0-5 sekunder)
        try {
            Thread.sleep(shotDelay); // Vänta i shotDelay millisekunder innan skottet utförs
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        shoot(1, row, col);
    }

    // Metoden för att utföra ett skott
    private void shoot(int player, int row, int col) {
        Board targetBoard = (player == 1) ? opponentBoard : playerBoard;

        if (row >= 0 && row < targetBoard.getRows() && col >= 0 && col < targetBoard.getCols()) {
            if (!targetBoard.hasBeenFired(row, col)) {
                targetBoard.shoot(row, col);

                char result = (char) targetBoard.getCell(row, col);
                if (result == 'X') {
                    if (targetBoard.isShipSunk(targetBoard.getCell(row, col))) {
                        System.out.println("Spelare " + player + " sänkte ett skepp!");
                    } else {
                        System.out.println("Spelare " + player + " träffade!");
                    }
                } else {
                    System.out.println("Spelare " + player + " missade!");
                }
            }
        }
    }

    public boolean isGameOver() {
        boolean player1Lost = opponentBoard.allShipsSunk();
        boolean player2Lost = playerBoard.allShipsSunk();

        if (player1Lost || player2Lost) {
            if (player1Lost && player2Lost) {
                System.out.println("Spelet slutade oavgjort!");
            } else if (player1Lost) {
                System.out.println("Spelare 2 vann!");
            } else {
                System.out.println("Spelare 1 vann!");
            }
            return true;
        }

        return false;
    }

    public String getCurrentPlayer() {
        return null;
    }
}
