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

import static java.lang.Math.abs;

/**
 * Author: Linus Lagerhjelm & Fredrik Johansson
 * File: Level
 * Created: 16-11-27
 * Description: Level holds the system of entities and handle their interactions
 *              updates and communication. Level is a builder and created by
 *              setting up all it's add methods
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

    /**
     * Setup up a Level with its most basic information
     * @param name Name of level
     * @param height Height of level
     * @param width Width of level
     * @param troupesToWin Amount of troupes to win the level
     * @param texture A path to an image which should be drawn as the level
     */
    public Level(String name, int height, int width,
                 int troupesToWin, String texture) {

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


    /**
     * Clone a level is a great way to simple have a copy of the level or
     * reset the level
     * @return A exact copy of the current level
     */
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

    /**
     * Returns a copy level in its initial state
     * @return A reseted level
     */
    public Level reset() {
        return initialState.clone();
    }

    /**
     * Update the level and all its entities. Makes sure all appropriate
     * interaction happens between entities, such as {@link Entity#interact()}
     * @param dt Delta time, time between calls to update
     */
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
                if (onPad(pad, troupe)) {
                    pad.landOn(troupe);
                }
            }
        }

        for (Troupe toRemove : troupesToRemove) {
            troupes.remove(toRemove);
        }
    }

    /**
     * Checks if a troupe is on a pad
     * @param pad Pad to check with
     * @param troupe Troupe to check
     * @return if a troupe is on a pad
     */
    public boolean onPad(Pad pad, Troupe troupe) {
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

    /**
     * Sets all the listeners for the entities, to ensure Level receives
     * all the events
     */
    private void setEntityListeners() {
        troupes.forEach(troupe -> {
            troupe.setGoalListener(this);
            troupe.setKilledListener(this);
            troupe.setPadSpawnedListener(this);
        });
        towers.forEach(tower -> tower.setShootListener(this));
    }

    /**
     * Sets a listener which is called whenever this level is won, i.e
     * when enough troupes has reached goals to meet condition.
     * Only one listener can be set
     * @param winListener Listener for when level is won
     */
    public void setWinListener(WinListener winListener) {
        this.winListener = winListener;
    }

    /**
     * Place all the added towers in random positions within set tower zones.
     * The randomness is weighted on the tower zones sizes.
     */
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

    /**
     * Send events from the system that the Level has to know about and
     * act upon.
     * @param levelEvents the broadcasted event
     */
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
                    // No feedback is given to user as of this point
                }
            } else if (event instanceof NewPadEvent) {
                NewPadEvent e = ((NewPadEvent)event);
                createPadFromEvent(e);
            } else if (event instanceof MouseClickEvent) {
                onClick(((MouseClickEvent) event).getPosition());
            }
        });
    }

    /**
     * Infers the type of pad to create based on the event and returns the
     * new pad
     * @param e the broadcasted event
     */
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

    /**
     * Inserts a teleport pad into the level:s list of entities
     * @param pad the pad to insert
     */
    private void addTeleportPad(TeleportPad pad) {
        Node nextNode = path.getNextNodeFrom(pad.getPosition());
        pad.setNextNode(nextNode);

        if (teleportPads.size() % 2 != 0) {
            TeleportPad previous = teleportPads.get(teleportPads.size() - 1);

            if (abs(previous.getLengthToStart() - pad.getLengthToStart()) <
                    pad.getHeight() + pad.getWidth()) {
                return;
            } else if (previous.getLengthToStart() < pad.getLengthToStart()) {
                previous.setTarget(pad);
            } else {
                pad.setTarget(previous);
            }
        }
        teleportPads.add(pad);
        pads.add(pad);
    }

    /**
     * Returns the current balance on the user's account on this level
     * @return formatted currency string
     */
    public String getMoney() {
        return player.getCurrency();
    }

    /**
     * Triggers a click event on this level which is propagated to all the
     * relevant entities
     * @param positionClicked position of click event
     */
    public void onClick(Position positionClicked) {
        double clickRange = 30;

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

    /**
     * {@inheritDoc}
     */
    @Override
    public void onKilled(Troupe troupe) {
        final double earnModifier = 1;

        troupesToRemove.add(troupe);
        player.addCurrency(
                new Currency((int) Math.round(troupe.getLengthWalked()*earnModifier)));
    }

    /**
     * {@inheritDoc}
     */
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

    /**
     * {@inheritDoc}
     */
    @Override
    public void onPadSpawned(Pad pad) {

        if (pad instanceof TeleportPad) {
            addTeleportPad((TeleportPad) pad);
        } else {
            pads.add(pad);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onShoot(Position from, Position to) {
        shots.add(new Line(from, to));
    }

    /**
     * Returns true/false if the user has won this level
     * @return boolean
     */
    public boolean hasWon() {
        return troupesInGoal >= troupesToWin;
    }

    /**
     * Returns the current user score on this level
     * @return Score
     */
    public Score getScore() {
        int elapsedSeconds = (int)(new Date().getTime() - startTime)/1000;
        return new Score(Math.max(MAX_SCORE - elapsedSeconds, 0));
    }

    /**
     * Returns true/false if the user has lost this level
     * @return boolean
     */
    public boolean hasLost() {
        boolean a = player.canAfford(TroupeFactory.getCheapestCost());
        boolean b = troupes.size() > 0;
        return !a && !b;
    }

    /**
     * Tells this level to start counting scores
     */
    public void start() {
        startTime = new Date().getTime();
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

    /**
     * Sets the initial state of this level
     * @param initialState state
     */
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
