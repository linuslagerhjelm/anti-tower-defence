package model.level;

import controller.eventhandler.events.NewPadEvent;
import controller.eventhandler.events.SpawnEvent;
import controller.eventhandler.events.SystemEvent;
import exceptions.NoSuchPadException;
import exceptions.NotEnoughFoundsException;
import model.entities.Pad;
import model.entities.PadFactory;
import model.entities.Path;
import model.entities.TeleportPad;
import model.entities.tower.Tower;
import model.entities.tower.TowerZone;
import model.entities.troupe.Troupe;
import model.entities.troupe.TroupeFactory;
import model.highscore.Score;
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

    private WinListener winListener;


    public interface WinListener {
        void onWin(Score score);
    }

    private final int MAX_SCORE = Integer.MAX_VALUE;
    private final String name;
    private final int height;
    private final int width;
    private final int troupesToWin;
    private int troupesInGoal = 0;
    private Level initialState;

    private List<TowerZone> towerZones;
    private Set<Tower> towers = new HashSet<>();
    private Set<Troupe> troupes = new LinkedHashSet<>();
    private Set<Troupe> troupesToRemove = new HashSet<>();
    private List<Line> shots = new ArrayList<>();
    private List<Pad> pads = new ArrayList<>();
    private List<TeleportPad> teleportPads = new ArrayList<>();
    private Path path;
    private Player player = new Player();
    private long startTime;


    private boolean build = false;

    public Level(String name, int height, int width, int troupesToWin) {

        this.name = name;
        this.height = height;
        this.width = width;
        this.troupesToWin = troupesToWin;
    }

    public void update(double dt) {
        for (Pad pad : pads) {
            for (Troupe troupe : troupes) {
                if (onPad(pad, troupe)) {
                    pad.landOn(troupe);
                }
            }
        }

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
        this.towers.addAll(towers);
    }

    public void addTroupe(Troupe troupe) {
        troupe.setStartNode(path.getStartNode());
        troupes.add(troupe);
        troupe.setKilledListener(this);
        troupe.setGoalListener(this);
    }

    public void addPads(List<Pad> pads) {
        pads.forEach(pad -> {
            if (pad instanceof TeleportPad) {
                addTeleportPad((TeleportPad) pad);
            }
        });
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
        buildWithoutInitialState();
    }

    private void buildWithoutInitialState() {
        placeTowersInZones();
        setEntityListeners();
        build = true;
    }

    public Level clone() {
        Level level = new Level(name, height, width, troupesToWin);
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

        level.setInitialState(initialState);

        buildWithoutInitialState();

        return level;
    }

    public Level reset() {
        initialState.getPath().resetSwitches();
        return initialState.clone();
    }

    private void setEntityListeners() {
        troupes.forEach(troupe -> {
            troupe.setGoalListener(this);
            troupe.setKilledListener(this);
        });
        towers.forEach(tower -> tower.setShootListener(this));
    }

    public void setWinListener(WinListener winListener) {
        this.winListener = winListener;
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

                } catch (NotEnoughFoundsException e) {
                    /* TODO: give feedback to user */
                    System.out.println(1);
                }
            } else if (event instanceof NewPadEvent) {
                NewPadEvent e = ((NewPadEvent)event);
                createPadFromEvent(e);
            }
        });
    }

    private void createPadFromEvent(NewPadEvent e) {
        try {
            Pad pad = PadFactory.newInstance(e.getType());
            pad.setProperties(e.getX(), e.getY(), e.getWidth(), e.getHeight());
            pads.add(pad);
            if (e.getType().equalsIgnoreCase("teleportpad")) {
                addTeleportPad((TeleportPad) pad);
            }

        } catch (NoSuchPadException ignore) {
            /* Ignore */
        }
    }

    private void addTeleportPad(TeleportPad pad) {
        if (teleportPads.size() % 2 != 0) {
            teleportPads.get(teleportPads.size() - 1).setTarget(pad);
        }
        teleportPads.add(pad);
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
        troupesInGoal++;
        if (hasWon()) {
            if (winListener != null) {
                winListener.onWin(getScore());
            }
        }
    }

    public boolean hasWon() {
        return troupesInGoal >= troupesToWin;
    }

    public Score getScore() {
        int elapsedSeconds = (int)(new Date().getTime() - startTime)/1000;
        return new Score(Math.max(MAX_SCORE - elapsedSeconds, 0));
    }

    public boolean hasLost() {
        boolean a = player.canAfford(TroupeFactory.getCheapestCost());
        boolean b = troupes.size() > 0;
        return !a && !b;
    }

    public void start() {
        startTime = new Date().getTime();
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

    public void setInitialState(Level initialState) {
        this.initialState = initialState;
    }
}
