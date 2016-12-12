package model.level;

import controller.eventhandler.events.SpawnEvent;
import controller.eventhandler.events.SystemEvent;
import exceptions.NotEnoughFounds;
import model.entities.Pad;
import model.entities.Path;
import model.entities.tower.Tower;
import model.entities.tower.TowerZone;
import model.entities.troupe.Troupe;
import model.player.Currency;
import model.player.Player;

import java.util.*;

/**
 * Author: Linus Lagerhjelm
 * File: Level
 * Created: 16-11-27
 * Description:
 */
public class Level implements Troupe.KilledListener,
                              Troupe.GoalListener,
                              Tower.ShootListener, Cloneable {

    private Level initialState;

    private List<TowerZone> towerZones;
    private Set<Tower> towers = new HashSet<>();
    private Set<Troupe> troupes = new LinkedHashSet<>();
    private Set<Troupe> troupesToRemove = new HashSet<>();
    private List<Line> shots = new ArrayList<>();
    private List<Pad> pads;
    private Path path;
    private Player player = new Player();


    private boolean build = false;

    public Level() {}
    public Level(String name, int height, int width) {}

    public void update(double dt) {
        /*for (Pad pad : pads) {
            for (Troupe troupe : troupes) {
                if (onPad(pad, troupe)) {
                    pad.landOn(troupe);
                }
            }
        }*/

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


    public boolean onPad(Pad pad, Troupe troupe) {
        Position M = troupe.getPosition();

        // Upper left corner
        Position A = pad.getPosition();

        // Upper right corner
        Position B = new Position(A.getX() + pad.getWidth(), A.getY());

        // Lower right corner
        Position D = new Position(A.getX(), A.getX() + pad.getHeight());

        /*
        *  Using th formula: (0<AM⋅AB<AB⋅AB)∧(0<AM⋅AD<AD⋅AD)
        *  to determine weather the troupe position is inside
        *  the rectangular area of the pad as suggested by:
        *  http://math.stackexchange.com/a/190373
        */
        double[] AM = createVector(A, M);
        double[] AB = createVector(A, B);
        double[] AD = createVector(A, D);

        return ((0 < scalarProduct(AM, AB)) &&
                (scalarProduct(AM, AB) < scalarProduct(AB, AB))) &&
                ((0 < scalarProduct(AM, AD)) &&
                        (scalarProduct(AM, AD) < scalarProduct(AD, AD)));
    }

    public static double[] createVector(Position p1, Position p2) {
        double[] vector = new double[2];
        vector[0] = p2.getX() - p1.getX();
        vector[1] = p2.getY() - p1.getY();
        return vector;
    }

    public static double scalarProduct(double[] v1, double[] v2) {
        return ((v1[0] * v2[0]) + (v1[1] * v2[1]));
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
        troupe.setStartNode(path.getStartNode());
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
        initialState = this.clone();
    }

    public Level clone() {
        Level level = new Level();
        List<TowerZone> lTowerZones = new ArrayList<>();
        Set<Tower> ltowers = new HashSet<>();
        List<Pad> lpads = new ArrayList<>();

        towerZones.forEach(lTowerZones::add);
        towers.forEach(ltowers::add);
        pads.forEach(lpads::add);

        troupes.forEach(level::addTroupe);
        level.addTowerZones(lTowerZones);
        level.addTowers(ltowers);
        level.addPads(lpads);
        level.addPath(this.path);

        return level;
    }

    public Level reset() {
        initialState.getPath().resetSwitches();
        return initialState;
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
        if (levelEvents.size() == 0) {
            return;
        }

        levelEvents.forEach(event -> {
            if (event instanceof SpawnEvent) {
                try {
                    SpawnEvent e = ((SpawnEvent)event);
                    this.addTroupe(player.createTroupe(e.getTroupeType()));

                } catch (NotEnoughFounds e) {
                    /* TODO: give feedback to user */
                    System.out.println(1);
                }
            }
        });

    }

    public String getMoney() {
        return player.getCurrency();
    }

    @Override
    public void onKilled(Troupe troupe) {
        troupesToRemove.add(troupe);
        player.addCurrency(
                new Currency((int) Math.round(troupe.getLengthWalked())));
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
