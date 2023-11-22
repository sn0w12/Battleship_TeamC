package com.example.battleship_teamc;

public class Coordinate {
    private int col;
    private int row;

    public Coordinate() {}

    public Coordinate(int col, int row) {
        this.col = col;
        this.row = row;
    }

    @Override
    public String toString() {
        return "Coordinate{" +
                "col=" + col +
                ", row=" + row +
                '}';
    }
}