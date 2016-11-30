package level;

import entities.Pad;
import entities.Path;
import entities.Tower;
import entities.TowerZone;

import java.util.BitSet;
import java.util.List;

/**
 * Author: Linus Lagerhjelm
 * File: Level
 * Created: 16-11-27
 * Description:
 */
public class Level {
    List<TowerZone> towerZones;
    List<Tower> towers;
    List<Pad> pads;
    Path path;

    public Level(String name, int height, int width) {}
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
}