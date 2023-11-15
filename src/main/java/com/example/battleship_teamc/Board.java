package com.example.battleship_teamc;

import java.util.Arrays;

public class Board {
    private final char[][] grid; // Använd char för att representera spelplanen

    public Board(int rows, int cols) {
        grid = new char[rows][cols];
        initializeBoard();
    }

    public Board() {
        // Här kan du initialisera grid-arrayen med en standard storlek om det behövs
        int defaultRows = 10; // Ange önskat antal rader
        int defaultCols = 10; // Ange önskat antal kolumner
        grid = new char[defaultRows][defaultCols];
        initializeBoard();
    }

    private void initializeBoard() {
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                grid[i][j] = '.'; // 'O' for empty cells
            }
        }
    }

    public void clearBoard() {
        for (int i = 0; i < grid.length; i++) {
            Arrays.fill(grid[i], '.'); // Assuming 'O' is the character for an empty cell
        }
    }

    public void printBoard() {
        // Print the column headers
        System.out.print("  ");
        for (int i = 0; i < grid[0].length; i++) {
            System.out.print(i + " ");
        }
        System.out.println();

        // Print each row with its corresponding letter and cell status
        for (int i = 0; i < grid.length; i++) {
            char letter = (char) ('A' + i);
            System.out.print(letter + " ");
            for (int j = 0; j < grid[i].length; j++) {
                System.out.print(grid[i][j] + " ");  // Print the status of the cell
            }
            System.out.println();  // Newline at the end of each row
        }

        // Print a separator line
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
        grid[row][col] = 'S';
    }

    public boolean hasShip(int newRow, int newCol) {
        return grid[newRow][newCol] == 'S';
    }

    public void shoot(int row, int col) {
        if (grid[row][col] != 'X' && grid[row][col] != 'O') {
            if (grid[row][col] == 'S') {
                grid[row][col] = 'X'; // 'X' represents a hit on a ship
            } else {
                grid[row][col] = 'O'; // 'O' represents a miss
            }
        }
    }

    public char getCell(int row, int col) {
        return grid[row][col];
    }

    public boolean hasBeenFired(int row, int col) {
        return grid[row][col] != '.' && grid[row][col] != 'S';
    }

    public boolean isAllShipsSunk() {
        for (char[] chars : grid) {
            for (int j = 0; j < grid[0].length; j++) {
                if (chars[j] == 'S' || chars[j] == 'X') {
                    return false; // There is still an unsunk ship
                }
            }
        }
        return true; // All ships have been sunk
    }
}