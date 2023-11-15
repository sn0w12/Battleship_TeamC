package com.example.battleship_teamc;

import java.util.List;
import java.util.Random;

// created methods to check if a ship was or was not hit at a coordinate.- briana
public class HitChecker {

    private Board board;

    public ShotLog getShotLog() {
        return shotLog;
    }

    public void setShotLog(ShotLog shotLog) {
        this.shotLog = shotLog;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    private ShotLog shotLog;

    private int score;


    // Method to send  first shot.
    public Coordinate firstShot(){
        Coordinate c = rng();
        shotLog.logShot(c);
        System.out.println("ran first shot, added to list, printing list");
        for (int i = 0; i < shotLog.getFiredShots().size(); i++) {
            System.out.println(shotLog.getFiredShots().get(i).toString());
        }
        return c;
    }
   // If fired shots are found in the shotlog list it generates two new coordinates.
    public Coordinate sendShot() {
        Coordinate c = rng();
        System.out.println("Rand generated coordinate " + c.toString());
        while(shotLog.getFiredShots().contains(c)){
            System.out.println("C not unique, generating again");
            c = rng();
        }
        System.out.println("found unique coordinate, logging in list");
        //when unique coordinate found, log in list
        shotLog.logShot(c);
        System.out.println("printing shotlog list");
        for (int i = 0; i < shotLog.getFiredShots().size(); i++) {
            System.out.println(shotLog.getFiredShots().get(i).toString());
        }
        return c;
        // Returns unique coordinate
    }

    public String checkShot (Coordinate newShot){
        String result = "";
        if (board.hasShip(newShot.getRow(), newShot.getCol())){
            System.out.println("Ship was hit at " + newShot.toString());
            this.score--;
            System.out.println("Score is now: " + this.getScore());
        } else {
            System.out.println("no hit at " + newShot.toString());
            System.out.println("score is now: " + this.getScore());

        }
        return "";
    }



  /*  public String checkHit(int row, int col) {
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
    }*/

    public HitChecker() {
        this.shotLog = new ShotLog();
        // Now we have all lists needed for logging shots.
        this.score = 30;
    }

    public HitChecker(Board board) {
        this.board = board;
        this.shotLog = new ShotLog();
        /* At every hit this will decrease by one.
        All ships represented by 30 coordinates.
         */

        this.score = 30;
    }

    // Creates and returns a random coordinate.
      /* public Coordinate rng(){
        int row = (int)(Math.random() * 10);
        int col = (int)(Math.random() * 10);
        // Typecast a double to int.

        return new Coordinate(row, col);

    }*/
    public Coordinate rng (){
        Random rand = new Random();
        int row= rand.nextInt(10);
        int col= rand.nextInt(10);
        return new Coordinate(row, col);
    }

}
