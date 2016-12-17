package model.level;

import controller.eventhandler.events.MouseClickEvent;
import controller.eventhandler.events.NewPadEvent;
import controller.eventhandler.events.SpawnEvent;
import controller.eventhandler.events.SystemEvent;
import exceptions.NoSuchPadException;
import exceptions.NotEnoughFoundsException;
import model.entities.*;
import model.entities.tower.Tower;
import model.entities.tower.TowerZone;
import model.entities.troupe.Troupe;
import model.entities.troupe.TroupeFactory;
import model.highscore.Score;
import model.player.Currency;
import model.player.Player;

import java.util.*;

/**
 * Author: Linus Lagerhjelm & Fredrik Johansson
 * File: Level
 * Created: 16-11-27
 * Description:
 */
public class Level implements Troupe.KilledListener,
                              Troupe.GoalListener,
                              Troupe.PadSpawnListener,
                              Tower.ShootListener,
                              Cloneable {

    private WinListener winListener;

    /**
     * Listener for when a level is completed
     */
    public interface WinListener {
        void onWin(Score score);
    }

    private final int MAX_SCORE = Integer.MAX_VALUE;
    private final String name;
    private final int height;
    private final int width;
    private final int troupesToWin;
    private final String texture;
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

    public Level(String name, int height, int width, int troupesToWin, String texture) {

        this.name = name;
        this.height = height;
        this.width = width;
        this.troupesToWin = troupesToWin;
        this.texture = texture;
    }

    /* Builder part */
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
        troupe.setPadSpawnedListener(this);
    }

    public void addPads(List<Pad> pads) {
        pads.forEach(pad -> {
            if (pad instanceof TeleportPad) {
                addTeleportPad((TeleportPad) pad);
            }
        });
        this.pads = pads;
    }

    public void build() {
        placeTowersInZones();
        setEntityListeners();
        build = true;
    }



    @Override
    public Level clone() {
        Level level = new Level(name, height, width, troupesToWin, texture);
        List<TowerZone> lTowerZones = new ArrayList<>();
        Set<Tower> ltowers = new HashSet<>();
        List<Pad> lpads = new ArrayList<>();
        level.addPath(this.path);

        towerZones.forEach(lTowerZones::add);
        towers.forEach(ltowers::add);
        pads.forEach(lpads::add);

        troupes.forEach(level::addTroupe);
        level.addTowerZones(lTowerZones);
        level.addTowers(ltowers);
        level.addPads(lpads);

        level.setInitialState(initialState);

        level.build();

        return level;
    }

    public Level reset() {
        return initialState.clone();
    }

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

            for (Pad pad : pads) {
                if (onPad2(pad, troupe)) {
                    pad.landOn(troupe);
                }
            }
        }

        for (Troupe toRemove : troupesToRemove) {
            troupes.remove(toRemove);
        }
    }

    public boolean onPad2(Pad pad, Troupe troupe) {
        double x = troupe.getPosition().getX();
        double y = troupe.getPosition().getY();

        if (    x >= pad.getPosition().getX() &&
                x <= pad.getPosition().getX() + pad.getWidth() &&
                y >= pad.getPosition().getY() &&
                y <= pad.getPosition().getY() + pad.getHeight()
                ) {

            return true;
        } else {
            return false;
        }
    }

    private void setEntityListeners() {
        troupes.forEach(troupe -> {
            troupe.setGoalListener(this);
            troupe.setKilledListener(this);
            troupe.setPadSpawnedListener(this);
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
                }
            } else if (event instanceof NewPadEvent) {
                NewPadEvent e = ((NewPadEvent)event);
                createPadFromEvent(e);
            } else if (event instanceof MouseClickEvent) {
                onClick(((MouseClickEvent) event).getPosition());
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
        pad.setNextNode(path.getNextNodeFrom(pad.getPosition()));
    }

    public String getMoney() {
        return player.getCurrency();
    }

    public void onClick(Position positionClicked) {
        double clickRange = 20;

        for (Tower tower : towers) {
            if (positionClicked.inRange(tower.getPosition(), clickRange)) {
                tower.interact();
            }
        }

        for (Troupe troupe : troupes) {
            if (positionClicked.inRange(troupe.getPosition(), clickRange)) {
                troupe.interact();
            }
        }

        for (Switch switcher : path.getSwitches()) {
            if (positionClicked.inRange(switcher.getPosition(), clickRange)) {
                switcher.interact();
            }
        }
    }

    @Override
    public void onKilled(Troupe troupe) {
        final double earnModifier = 1;

        troupesToRemove.add(troupe);
        player.addCurrency(
                new Currency((int) Math.round(troupe.getLengthWalked()*earnModifier)));
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

    @Override
    public void onPadSpawned(Pad pad) {
        pads.add(pad);

        if (pad instanceof TeleportPad) {
            addTeleportPad((TeleportPad) pad);
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


    /* Getters */

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

    public String getTexture() {
        return texture;
    }

    public int getNumberInGoal() {
        return troupesInGoal;
    }

    public int goalRequirement() {
        return troupesToWin;
    }
}
