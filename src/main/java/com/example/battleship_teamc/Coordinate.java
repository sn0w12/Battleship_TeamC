package com.example.battleship_teamc;
// sparas varje skott som en koordinat - briana
public class Coordinate {

    private int col;

    private int row;

    public Coordinate() {
    }

    public Coordinate(int col, int row) {
        this.col = col;
        this.row = row;
    }

    public int getCol() {
        return col;
    }

    public void setCol(int col) {
        this.col = col;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
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
