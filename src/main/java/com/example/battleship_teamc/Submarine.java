package com.example.battleship_teamc;


public class Submarine extends Ship {
    public Submarine() {
    }

    public Submarine(String name, int size, boolean isDestroyed) {
        super(name, size, isDestroyed);
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
}
