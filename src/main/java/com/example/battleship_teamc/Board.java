package com.example.battleship_teamc;

import java.util.ArrayList;
import ships.*;

public class Board {
    private int[][] grid;
    ArrayList<String> firedShots;

    public Board(int rows, int cols) {
        grid = new int[rows][cols];
        firedShots = new ArrayList<>();
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
                System.out.print(grid[i][j] + " ");
            }
            System.out.println();
        }
    }

    public int getRows() {
        return grid.length;
    }

    public int getCols() {
        return grid[0].length;
    }

    public int[][] getGrid() {
        return grid;
    }

    private boolean isValidPosition(int row, int col) {
        return row >= 0 && row < grid.length && col >= 0 && col < grid[0].length;
    }

    public void addShip(int row, int col, Ship ship) {
        if (isValidPosition(row, col) && isSpaceAvailable(row, col, ship)) {
            if (ship.isHorizontal()) {
                for (int i = 0; i < ship.getSize(); i++) {
                    grid[row][col + i] = ship.getType();
                }
            } else {
                for (int i = 0; i < ship.getSize(); i++) {
                    grid[row + i][col] = ship.getType();
                }
            }
        } else {
            System.out.println("Invalid position or position already occupied.");
        }
    }

    boolean isSpaceAvailable(int row, int col, Ship ship) {
        int gridSize = grid.length;
        int shipSize = ship.getSize();

        for (int i = -1; i <= shipSize; i++) {
            for (int j = -1; j <= 1; j++) {
                int newRow = row + i;
                int newCol = col + j;

                if (newRow >= 0 && newRow < gridSize && newCol >= 0 && newCol < grid[0].length) {
                    if (grid[newRow][newCol] != 0) {
                        return false; // Some part of the space or surrounding space is already occupied
                    }
                }
            }
        }

        if (ship.isHorizontal()) {
            if (col + shipSize > grid[0].length) {
                return false; // The ship doesn't fit horizontally
            }
            for (int i = 0; i < shipSize; i++) {
                if (grid[row][col + i] != 0) {
                    return false; // Some part of the space is already occupied
                }
            }
        } else {
            if (row + shipSize > gridSize) {
                return false; // The ship doesn't fit vertically
            }
            for (int i = 0; i < shipSize; i++) {
                if (grid[row + i][col] != 0) {
                    return false; // Some part of the space is already occupied
                }
            }
        }

        return true;
    }

    String convertToCoordinate(int row, int col) {
        char letter = (char) ('A' + row);
        return letter + String.valueOf(col);
    }
}