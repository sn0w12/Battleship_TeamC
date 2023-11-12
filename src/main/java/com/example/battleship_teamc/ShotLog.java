package com.example.battleship_teamc;

import java.util.ArrayList;
import java.util.List;

public class ShotLog {

    private List<Coordinate> firedShots;

    private List<Coordinate> hits;

    private List<Coordinate> misses;

//created functionality to log and keep track of all shots. - briana

    public ShotLog() {
        this.firedShots = new ArrayList<>();
        this.misses = new ArrayList<>();
        this.hits = new ArrayList<>();

    }

    public ShotLog(List<Coordinate> firedShots, List<Coordinate> hits, List<Coordinate> misses) {
        this.firedShots = firedShots;
        this.hits = hits;
        this.misses = misses;
    }

    public void logShot (int row, int col){
        Coordinate coordinate = new Coordinate(row,col);
        this.firedShots.add(coordinate);
        System.out.println("added coordinate " + this.firedShots.get(0).toString());
    }

    public void logHit(int row, int col){
        Coordinate coordinate = new Coordinate(row,col);
        this.hits.add(coordinate);
        System.out.println("added coordinate " + this.hits.get(0).toString());
    }

    public void logMisses(int row, int col){
        Coordinate coordinate = new Coordinate(row,col);
        this.misses.add(coordinate);
        System.out.println("added coordinate " + this.misses.get(0).toString());

    }
    public List<Coordinate> getFiredShots() {
        return firedShots;
    }

    public void setFiredShots(List<Coordinate> firedShots) {
        this.firedShots = firedShots;
    }

    public List<Coordinate> getHits() {
        return hits;
    }

    public void setHits(List<Coordinate> hits) {
        this.hits = hits;
    }

    public List<Coordinate> getMisses() {
        return misses;
    }

    public void setMisses(List<Coordinate> misses) {
        this.misses = misses;
    }

    @Override
    public String toString() {
        return "ShotLog{" +
                "firedShots=" + firedShots +
                ", hits=" + hits +
                ", misses=" + misses +
                '}';
    }
}
