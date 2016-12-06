package model.level;

import controller.eventhandler.SystemEvent;
import model.entities.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Author: Linus Lagerhjelm
 * File: Level
 * Created: 16-11-27
 * Description:
 */
public class Level {
    private List<TowerZone> towerZones;
    private List<Tower> towers;
    private List<Troupe> troupes = new ArrayList<>();
    private List<Pad> pads;
    private Path path;


    public Level(String name, int height, int width) {}

    public void update(double dt) {
        for (Troupe troupe : troupes) {
            troupe.update(dt);
        }
    }

    public void addPath(Path p) {
        this.path = p;
    }

    public void addTowerZones(List<TowerZone> towerZones) {
        this.towerZones = towerZones;
    }

    public void addTowers(List<Tower> towers) {
        this.towers = towers;
    }

    public void addTroupe(Troupe troupe) {
        troupes.add(troupe);
    }

    public List<Troupe> getTroupes() {
        return troupes;
    }

    public void addPads(List<Pad> pads) {
        this.pads = pads;
    }

    public List<Pad> getPads() {
        return pads;
    }

    public List<Tower> getTowers() {
        return towers;
    }


    public List<TowerZone> getTowerZones() {
        return towerZones;
    }

    public Path getPath() {
        return path;
    }

    public void receiveEvents(List<SystemEvent> levelEvents) {

    }
}
