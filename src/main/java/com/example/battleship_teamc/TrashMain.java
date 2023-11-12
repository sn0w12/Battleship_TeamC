package com.example.battleship_teamc;
import java.util.Arrays;
/*TrashMain så man kan prova programmet i terminalen utan
att behöver kör hela client och server programmet mycket snabbare sätt att testa metoder.-briana
 */
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

        /*ShotLog shotLog = new ShotLog();

        shotLog.logShot(0,0);
        shotLog.logHit(0,0);
        shotLog.logMisses(0,6);
*/
    }

}
