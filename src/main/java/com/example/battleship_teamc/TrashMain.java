package com.example.battleship_teamc;
import java.util.Arrays;
/*TrashMain så man kan prova programmet i terminalen utan
att behöver kör hela client och server programmet mycket snabbare sätt att testa metoder.-briana
 */
public class TrashMain {
    public static void  main(String[] args){
        Board testBoard = new Board();
        HitChecker hitChecker = new HitChecker(testBoard);

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

        System.out.println(hitChecker.firstShot(0, 0));
        System.out.println(hitChecker.checkHit(1,1));


        System.out.println(Arrays.deepToString(testBoard.getGrid()));

        /*ShotLog shotLog = new ShotLog();

        shotLog.logShot(0,0);
        shotLog.logHit(0,0);
        shotLog.logMisses(0,6);
*/
    }

}
