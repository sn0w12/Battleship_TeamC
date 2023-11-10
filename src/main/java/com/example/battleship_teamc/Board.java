
package com.example.battleship_teamc;

import java.util.ArrayList;

public class Board {
    private char[][] grid; // Använd char för att representera spelplanen
    private ArrayList<String> firedShots;

    public Board(int rows, int cols) {
        grid = new char[rows][cols];
        initializeBoard();
        firedShots = new ArrayList<>();
    }

    public Board() {
        // Här kan du initialisera grid-arrayen med en standard storlek om det behövs
        int defaultRows = 10; // Ange önskat antal rader
        int defaultCols = 10; // Ange önskat antal kolumner
        grid = new char[defaultRows][defaultCols];
        initializeBoard();
        firedShots = new ArrayList<>();
    }

    private void initializeBoard() {
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                grid[i][j] = 'O'; // 'O' for empty cells
            }
        }
    }


    public void printBoard() {
        System.out.print("  ");
        for (int i = 0; i < grid[0].length; i++) {
            System.out.print(i + " ");
        }
        System.out.println();

        for (int i = 0; i < grid.length; i++) {
            char letter = (char) ('A' + i);
            System.out.print(letter + " ");
            for (int j = 0; j < grid[0].length; j++) {
                char cell = grid[i][j];
                if (cell == 'S') {
                    System.out.print('S' + " "); // 'S' for ships
                } else if (cell == 'X') {
                    System.out.print('X' + " "); // 'X' for hit ships
                } else if (cell == 'O') {
                    System.out.print('O' + " "); // 'O' for empty cells
                } else {
                    System.out.print("  "); // Two spaces for other symbols
                }
            }
            System.out.println();

        }
        System.out.println("----------------------------");
    }






    public int getRows() {
        return grid.length;
    }

    public int getCols() {
        return grid[0].length;
    }

    public char[][] getGrid() {
        return grid;
    }

    private boolean isValidPosition(int row, int col) {
        return row >= 0 && row < grid.length && col >= 0 && col < grid[0].length;
    }

    public void placeShip(int row, int col) {
        grid[row][col] = 'S'; // Använd '*' för att representera en del av ett skepp
    }

    public boolean hasShip(int newRow, int newCol) {
        return grid[newRow][newCol] == 'S';
    }
// Lagt till bool om det var hit eller miss -Briana
    public boolean shoot(int row, int col) {
        if (grid[row][col] == 'S') {
            grid[row][col] = 'X'; // 'X' representerar träff på ett skepp
            return true;
        } else {
            grid[row][col] = 'O'; // 'O' representerar miss
            return false;
        }
    }

    public char getCell(int row, int col) {
        return grid[row][col];
    }

    public boolean hasBeenFired(int row, int col) {
        return false;
    }

    public boolean isAllShipsSunk() {
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                if (grid[i][j] == 'S' || grid[i][j] == 'X') {
                    return false; // There is still an unsunk ship
                }
            }
        }
        return true; // All ships have been sunk
    }

}