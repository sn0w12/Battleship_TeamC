package com.example.battleship_teamc;

import java.util.List;
// created methods to check if a ship was or was not hit at a coordinate.- briana
public class HitChecker {

    private Board board;
    private ShotLog shotLog;

    public boolean firstShot(int row, int col){
        boolean bool;
        this.shotLog.logShot(row, col);
        if (this.board.shoot(row, col)) {
            System.out.println("Ship was hit at " + row + "," + col);
            this.shotLog.logShot(row, col);
            this.shotLog.logHit(row, col);
            bool = true;
        } else {
            System.out.println("Not hit at " + row + "," + col);
            this.shotLog.logShot(row, col);
            this.shotLog.logMisses(row, col);
            bool = false;
        }
        return bool;
    }

    public String checkHit(int row, int col) {
        String status = " ";
            for (int i = 0; i < this.shotLog.getFiredShots().size(); i++) {
                if (row == this.shotLog.getFiredShots().get(i).getRow()
                        && col == this.shotLog.getFiredShots().get(i).getCol()) {
                    System.out.println("Shot already in list, Try again!");
                    status = "fail";
                } else {
                    System.out.println("Coordinate unique");
                    if (this.board.shoot(row, col)) {
                        System.out.println("Ship was hit at " + row + "," + col);
                        shotLog.logShot(row, col);
                        shotLog.logHit(row, col);
                        status = "hit";
                    } else {
                        System.out.println("Not hit at " + row + "," + col);
                        shotLog.logShot(row, col);
                        shotLog.logMisses(row, col);
                        status = "miss";
                    }
                }


        }

        return status;
    }

    public HitChecker() {
        this.shotLog = new ShotLog(); // Now we have all lists needed for logging shots.
    }

    public HitChecker(Board board) {
        this.board = board;
        this.shotLog = new ShotLog();
    }
}
