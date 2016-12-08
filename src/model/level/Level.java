package model.level;

import controller.eventhandler.events.SystemEvent;
import model.entities.*;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/**
 * Author: Linus Lagerhjelm
 * File: Level
 * Created: 16-11-27
 * Description:
 */
public class Level implements Troupe.KilledListener, Troupe.GoalListener{
    private List<TowerZone> towerZones;
    private List<Tower> towers;
    private Set<Troupe> troupes = new LinkedHashSet<>();
    private Set<Troupe> troupesToRemove = new HashSet<>();
    private List<Pad> pads;
    private Path path;


    public Level(String name, int height, int width) {}

    public void update(double dt) {
        for (Troupe troupe : troupes) {
            troupe.update(dt);
        }
        for (Troupe toRemove : troupesToRemove) {
            troupes.remove(toRemove);
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
        troupe.setKilledListener(this);
        troupe.setGoalListener(this);
    }

    public Set<Troupe> getTroupes() {
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
        if (levelEvents.size() > 0) {
            Troupe t = new TeleportTroupe();
            t.setStartNode(path.getStartNodes().get(0));
            this.addTroupe(t);
        }
    }

    @Override
    public void onKilled(Troupe troupe) {
        troupesToRemove.add(troupe);
    }

    @Override
    public void onGoal(Troupe troupe) {
        troupesToRemove.add(troupe);
    }
}
