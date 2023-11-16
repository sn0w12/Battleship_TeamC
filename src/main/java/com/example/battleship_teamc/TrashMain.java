package com.example.battleship_teamc;
import java.util.Arrays;

public class TrashMain {
    public static void  main(String[] args){
        Board testBoard = new Board();
        HitChecker hitChecker = new HitChecker(testBoard);

        //testing first and second shot methods, and if shots are logged - Briana
        hitChecker.firstShot();
        hitChecker.sendShot();

        System.out.println(hitChecker.getShotLog().getFiredShots().size());



        // place test ships

        for (int i = 0; i < 5; i++) {
            testBoard.placeShip(0, i);
        }
        for (int i = 0; i < 4 ; i++) {
            testBoard.placeShip(2,i);
        }
        for (int i = 0; i < 3 ; i++) {
            testBoard.placeShip(4,i);
        }

        //testing method for checking enemy shot, and changing grid value from S to X - Briana
Coordinate c = new Coordinate(0, 0);
        hitChecker.checkShot(c);

        System.out.println(Arrays.deepToString(testBoard.getGrid()));

    }

}
