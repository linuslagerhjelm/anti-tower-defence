/*
 * File: TeleportTroupe.java
 * Author: Fredrik Johansson
 * Date: 2016-11-30
 */
package entities;

import level.Position;

public class TeleportTroupe implements Troupe {

    public static final Stats STATS = new Stats(10, 12);


    private Path path;
    private KilledListener listener;
    private Position position;
    private int health = STATS.getHealth();

    @Override
    public void setPath(Path path) {
        this.path = path;
        int x = path.getStartNodes().get(0).x;
        int y = path.getStartNodes().get(0).y;
        position = new Position(x, y);
    }

    @Override
    public void setKilledListener(KilledListener listener) {
        this.listener = listener;
    }

    @Override
    public void update(double dt) {
        position.setX(30);
    }

    @Override
    public void interact() {

    }

    @Override
    public void setPosition(Position position) {
        this.position = position;
    }

    @Override
    public Position getPosition() {
        return position;
    }

    @Override
    public void receiveDamage(int damage) {
        health -= damage;
        if (health <= 0 && listener != null) {
            listener.onKilled(this);
        }
    }

    @Override
    public Stats getStats() {
        return STATS;
    }
}
