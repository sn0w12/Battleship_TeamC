package ships;

public abstract class Ship {



    private String name;
    private int size;
    private boolean isDestroyed;

    public Ship() {
    }

    public Ship(String name, int size, boolean isDestroyed) {
        this.name = name;
        this.size = size;
        this.isDestroyed = isDestroyed;
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

    public void setSize(int size) {
        this.size = size;
    }

    public boolean isDestroyed() {
        return isDestroyed;
    }

    public void setDestroyed(boolean destroyed) {
        isDestroyed = destroyed;
    }

    @Override
    public String toString() {
        return "Ship{" +
                "name='" + name + '\'' +
                ", size=" + size +
                ", Is Destroyed=" + isDestroyed +
                '}';
    }
}