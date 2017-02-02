/*
 * File: TeleportTroupe.java
 * Author: Fredrik Johansson
 * Date: 2016-11-30
 */
package model.entities.troupe;

import model.entities.Node;
import model.entities.Pad;
import model.entities.TeleportPad;
import model.level.Position;

/**
 * A TeleportTroupe have the ability to lay out TeleportPads. But cost more
 * and have less health.
 */
class TeleportTroupe implements Troupe {

    public static final TroupeStats STATS = new TroupeStats(100, 29, 300,
            "Troupe that places pads that teleports troupes",
            "Wizard", "/images/troops/wizard.png");


    private KilledListener killedListener;
    private GoalListener goalListener;
    private PadSpawnListener padSpawnListener;
    private Position position;
    private int health = STATS.getHealth();
    private Node currentNode;
    private double walkedLength;

    /**
     * {@inheritDoc}
     */
    @Override
    public void setStartNode(Node start) {

        if (start.hasSuccessor()) {
            currentNode = start.getNext();

        } else if (start.isGoal()) {
            if (goalListener != null) {
                goalListener.onGoal(this);
            }

        } else {
            if (killedListener != null) {
                killedListener.onKilled(this);
            }
        }
        position = start.getPosition().clone();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setNextNode(Node next) {
        currentNode = next;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setKilledListener(KilledListener listener) {
        this.killedListener = listener;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setGoalListener(GoalListener listener) {
        this.goalListener = listener;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setPadSpawnedListener(PadSpawnListener listener) {
        this.padSpawnListener = listener;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void update(double dt) {
        double angle = position.angle(currentNode.getPosition());
        double nextX = nextX(angle, dt);
        double nextY = nextY(angle, dt);
        walkedLength += position.lengthTo(new Position(nextX, nextY));
        position.setX(nextX);
        position.setY(nextY);


        double newAngle = position.angle(currentNode.getPosition());
        if (angle - newAngle > 0.001) { // walked past node
            if (currentNode.isGoal()) {
                if (goalListener != null) {
                    goalListener.onGoal(this);
                }

            } else if (currentNode.hasSuccessor()) {
                currentNode = currentNode.getNext();

            } else {
                if (killedListener != null) {
                    killedListener.onKilled(this);
                }
            }
        }
    }

    /**
     * On interaction this troupe will lay out TeleportPad {@link TeleportPad}
     */
    @Override
    public void interact() {
        Pad pad = new TeleportPad(getPosition().getX()-5,
                                  getPosition().getY()-5, 10, 10);
        if (padSpawnListener != null) {
            padSpawnListener.onPadSpawned(pad);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setPosition(Position position) {
        this.position = position.clone();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Position getPosition() {
        return position;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getFilePath() {
        return STATS.getImgPath();
    }

    /**
     * {@inheritDoc}
     *
     * Will remove health directly from damage
     */
    @Override
    public void receiveDamage(int damage) {
        health -= damage;
        if (health <= 0 && killedListener != null) {
            killedListener.onKilled(this);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TroupeStats getStats() {
        return STATS;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public double getLengthWalked() {
        return walkedLength;
    }

    /**
     * Get x position based on current x, speed, angle and delta time
     * @param angle Current angle in radians
     * @param dt Time from previous update
     * @return New x position
     */
    private double nextX(double angle, double dt) {
        return position.getX() + Math.cos(angle)*STATS.getSpeed()*dt;
    }

    /**
     * Get y position based on current y, speed, angle and delta time
     * @param angle Current angle in radians
     * @param dt Time from previous update
     * @return New y position
     */
    private double nextY(double angle, double dt) {
        return position.getY() + Math.sin(angle)*STATS.getSpeed()*dt;
    }
}
