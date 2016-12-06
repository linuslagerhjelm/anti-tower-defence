package model.level;

import controller.eventhandler.SystemEvent;
import model.entities.Pad;
import model.entities.Path;
import model.entities.Tower;
import model.entities.TowerZone;

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
    private List<Pad> pads;
    private Path path;


    public Level(String name, int height, int width) {}

    public void update(long dt) {}

    public void addPath(Path p) {
        this.path = p;
    }

    public void addTowerZones(List<TowerZone> towerZones) {
        this.towerZones = towerZones;
    }

    public void addTowers(List<Tower> towers) {
        this.towers = towers;
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
