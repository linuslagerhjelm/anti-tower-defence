package model.level;

import controller.eventhandler.events.SystemEvent;
import javafx.geometry.Pos;
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
public class Level implements Troupe.KilledListener, Troupe.GoalListener {
    private List<TowerZone> towerZones;
    private List<Tower> towers;
    private Set<Troupe> troupes = new LinkedHashSet<>();
    private Set<Troupe> troupesToRemove = new HashSet<>();
    private List<Pad> pads;
    private Path path;


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
