
package ships;

public abstract class Ship {
    private String name;
    private int size;
    private boolean isDestroyed;
    private boolean isPlaced; // Lägg till en flagga för att markera om skeppet är placerat
    private int type;
    public Ship() {
    }

    public Ship(String name, int size, boolean isDestroyed, int type) {
        this.name = name;
        this.size = size;
        this.isDestroyed = isDestroyed;
        this.isPlaced = false; // Skeppet anses inte vara placerat när det skapas
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSize() {
        return size;
    }

    public int getType() {
        return type;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public boolean isDestroyed() {
        return isDestroyed;
    }

    public void setDestroyed(boolean destroyed) {
        isDestroyed = destroyed;
    }

    public void setPlaced(boolean placed) {
        isPlaced = placed;
    }

    // Metod för att kontrollera om skeppet är placerat
    public boolean isPlaced() {
        return isPlaced;
    }

    @Override
    public String toString() {
        return "Ship{" +
                "name='" + name + '\'' +
                ", size=" + size +
                ", Is Destroyed=" + isDestroyed +
                ", isPlaced=" + isPlaced +
                '}';
    }
}