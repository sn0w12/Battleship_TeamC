package com.example.battleship_teamc;

public class HitChecker {

    public Board board;

    public boolean checkHit( int row, int col) {
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
