package ships;

public class Battleship extends Ship{

    public Battleship(String name, int size, boolean isDestroyed, int type) {
        super(name, size, isDestroyed, type);
    }

    @Override
    public String getName() {
        return super.getName();
    }
    @Override
    public void setName(String name) {
        super.setName(name);
    }
    @Override
    public int getSize() {
        return super.getSize();
    }
    @Override
    public void setSize(int size) {
        super.setSize(size);
    }
    @Override
    public boolean isDestroyed() {
        return super.isDestroyed();
    }
    @Override
    public void setDestroyed(boolean destroyed) {
        super.setDestroyed(destroyed);
    }
    @Override
    public String toString() {
        return super.toString();
    }
    @Override
    public int getType() { return super.getType(); }
}
