package com.example.battleship_teamc;
import java.util.Arrays;
//TrashMain så man kan prova programmet i terminalen utan client och server så allt funkar
public class TrashMain {
    public static void  main(String[] args){
        Board testBoard = new Board();
        HitChecker hitChecker = new HitChecker(testBoard);

        testBoard.placeShip(0,0);
        testBoard.placeShip(0,1);
        testBoard.placeShip(0,2);
        testBoard.placeShip(0,3);
        testBoard.placeShip(0,4);


        hitChecker.checkHit(0,0);

        System.out.println(Arrays.deepToString(testBoard.getGrid()));
    }

}
