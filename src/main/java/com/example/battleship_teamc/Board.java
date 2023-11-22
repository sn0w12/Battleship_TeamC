package com.example.battleship_teamc;

import java.util.ArrayList;
import java.util.Arrays;

public class Board {
    private final char[][] grid; // Använd char för att representera spelplanen
    private final ArrayList<String> firedShots;

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
                grid[i][j] = '.'; // 'O' for empty cells
            }
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

    public void placeShip(int row, int col) {
        grid[row][col] = 'S'; // Använd '*' för att representera en del av ett skepp
    }

    public boolean hasShip(int newRow, int newCol) {
        return grid[newRow][newCol] == 'S';
    }

    public char getCell(int row, int col) {
        return grid[row][col];
    }

    //marks a hit cell with X - Briana
    public void markHit(int row, int col, char newVal) {
        grid[row][col] = newVal;
    }

    public boolean isAllShipsSunk() {
        for (char[] chars : grid) {
            for (int j = 0; j < grid[0].length; j++) {
                if (chars[j] == 'S') {
                    return false; // There is still an unsunk ship
                }
            }
        }
        return true; // All ships have been sunk
    }

    public void clearBoard() {
        for (int i = 0; i < grid.length; i++) {
            Arrays.fill(grid[i], '.');
        }
    }
}