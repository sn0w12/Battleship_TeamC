package com.example.battleship_teamc;
import java.util.List;
import java.util.Random;

// created methods to check if a ship was or was not hit at a coordinate.- briana
public class HitChecker {

    private Board board;
    private ShotLog shotLog;
    private int score;

    public int getScore() {return score;}

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

    public HitChecker() {
        this.shotLog = new ShotLog();
        // Now we have all lists needed for logging shots.
        this.score = 30;
    }

    public HitChecker(Board board) {
        this.board = board;
        this.shotLog = new ShotLog();
        this.score = 30;
        /* At every hit this will decrease by one.
        All ships represented by 30 coordinates.
         */
    }

    public Coordinate rng (){
        Random rand = new Random();
        int row= rand.nextInt(10);
        int col= rand.nextInt(10);
        return new Coordinate(row, col);
    }
}
