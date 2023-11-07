package ships;

import java.util.ArrayList;
import java.util.List;

public class Fleet {

    private List<Ship> playerFleet;

    public Fleet() {
        this.playerFleet = new ArrayList<Ship>();

        AircraftCarrier aircraftCarrier = new AircraftCarrier("AircraftCarrier", 5,false, 5);

        Battleship battleship1 = new Battleship("Battleship1", 4, false, 4);
        Battleship battleship2 = new Battleship("Battleship2", 4, false, 4);

        Cruiser cruiser1 = new Cruiser("Cruiser1", 3, false, 3);
        Cruiser cruiser2 = new Cruiser("Cruiser2", 3, false, 3);
        Cruiser cruiser3 = new Cruiser("Cruiser3", 3, false, 3);

        Submarine submarine1 = new Submarine("Submarine1", 2, false, 2);
        Submarine submarine2 = new Submarine("Submarine2", 2, false, 2);
        Submarine submarine3 = new Submarine("Submarine3", 2, false, 2);
        Submarine submarine4 = new Submarine("Submarine4", 2, false, 2);

       this.playerFleet.add(aircraftCarrier);

       this.playerFleet.add(battleship1);
       this.playerFleet.add(battleship2);

       this.playerFleet.add(cruiser1);
       this.playerFleet.add(cruiser2);
       this.playerFleet.add(cruiser3);

       this.playerFleet.add(submarine1);
       this.playerFleet.add(submarine2);
       this.playerFleet.add(submarine3);
       this.playerFleet.add(submarine4);

    }

    public Fleet(List<Ship> playerFleet) {
        this.playerFleet = playerFleet;
    }

    public List<Ship> getPlayerFleet() {
        return playerFleet;
    }

    public void setPlayerFleet(List<Ship> playerFleet) {
        this.playerFleet = playerFleet;
    }

    @Override
    public String toString() {
        return "Fleet{" +
                "playerFleet=" + playerFleet +
                '}';
    }
}
