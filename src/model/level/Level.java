package model.level;

import controller.eventhandler.events.SystemEvent;
import model.entities.Pad;
import model.entities.Path;
import model.entities.tower.Tower;
import model.entities.tower.TowerZone;
import model.entities.troupe.TeleportTroupe;
import model.entities.troupe.Troupe;

import java.util.*;

/**
 * Author: Linus Lagerhjelm
 * File: Level
 * Created: 16-11-27
 * Description:
 */
public class Level implements Troupe.KilledListener,
                              Troupe.GoalListener,
                              Tower.ShootListener {

    private List<TowerZone> towerZones;
    private Set<Tower> towers = new HashSet<>();
    private Set<Troupe> troupes = new LinkedHashSet<>();
    private Set<Troupe> troupesToRemove = new HashSet<>();
    private List<Line> shots = new ArrayList<>();
    private List<Pad> pads;
    private Path path;

    private boolean build = false;


    public Level(String name, int height, int width) {}

    public void update(double dt) {
        for (Troupe troupe : troupes) {
            troupe.update(dt);

            for (Tower tower : towers) {
                if (tower.getPosition().inRange(
                        troupe.getPosition(),
                        tower.getStats().getRange())) {

                    tower.inRange(troupe);
                }
            }
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

    public void addTowers(Collection<Tower> towers) {
        for (Tower tower : towers) {
            tower.addShootListener(this);
            this.towers.add(tower);
        }
    }

    public void addTroupe(Troupe troupe) {
        troupes.add(troupe);
        troupe.setKilledListener(this);
        troupe.setGoalListener(this);
    }

    public void addPads(List<Pad> pads) {
        this.pads = pads;
    }

    public Set<Troupe> getTroupes() {
        return troupes;
    }

    public List<Pad> getPads() {
        return pads;
    }

    public Collection<Tower> getTowers() {
        return towers;
    }


    public List<TowerZone> getTowerZones() {
        return towerZones;
    }

    public Path getPath() {
        return path;
    }

    public List<Line> getShots() {
        return shots;
    }

    public void resetShots() {
        shots.clear();
    }

    public void build() {
        placeTowersInZones();
        build = true;
    }

    private void placeTowersInZones() {
        Random generator = new Random();

        int totalSize = 0;
        TreeMap<Integer, TowerZone> sizes = new TreeMap<>();
        for (TowerZone zone : towerZones) {
            sizes.put(totalSize, zone);
            totalSize += zone.size();
        }
        for (Tower tower : towers) {
            int chosenTowerZoneIndex = randomIntInRange(generator, 0, totalSize);
            Integer chosenTowerZoneKey = sizes.floorKey(chosenTowerZoneIndex);
            TowerZone chosenTowerZone = sizes.get(chosenTowerZoneKey);
            tower.setPosition(randomInArea(generator, chosenTowerZone));
        }
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

    @Override
    public void onShoot(Position from, Position to) {
        shots.add(new Line(from, to));
    }

    private int randomIntInRange(Random generator, int min, int max) {
        return generator.nextInt((max - min) + 1) + min;
    }

    private double randomDoubleInRange(Random generator, double min, double max) {
        return min + (max - min) * generator.nextDouble();
    }

    private Position randomInArea(Random generator, TowerZone zone) {
        double x = randomDoubleInRange(generator, zone.getX(),
                                                  zone.getX() +zone.getWidth());
        double y = randomDoubleInRange(generator, zone.getY(),
                                                  zone.getY()+zone.getHeight());
        return new Position(x, y);
    }
}
