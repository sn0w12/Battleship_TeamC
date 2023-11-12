package com.example.battleship_teamc;

import java.util.List;
// created methods to check if a ship was or was not hit at a coordinate.- briana
public class HitChecker {

    private Board board;

    private List<Coordinate> hits;

    private List<Coordinate> misses;

    public HitChecker(List<Coordinate> hits, List<Coordinate> misses) {
        this.hits = hits;
        this.misses = misses;
    }

    public boolean checkHit(int row, int col) {
        if(this.board.shoot(row, col)){
            System.out.println("Ship was hit at " + row + "," + col);
            return true;
        }else {
            System.out.println("Not hit at " + row + "," + col);
            return false;
        }
    }

    public HitChecker() {
    }

    public HitChecker(Board board) {
        this.board = board;
    }
}
