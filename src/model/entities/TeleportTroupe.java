/*
 * File: TeleportTroupe.java
 * Author: Fredrik Johansson
 * Date: 2016-11-30
 */
package model.entities;

import model.level.Position;

import java.util.NoSuchElementException;

public class TeleportTroupe implements Troupe {

    public static final Stats STATS = new Stats(100, 12);


    private KilledListener killedListener;
    private GoalListener goalListener;
    private Position position;
    private int health = STATS.getHealth();
    private Node currentNode;

    @Override
    public void setStartNode(Node start) {
        currentNode = start;
        int x = currentNode.getX();
        int y = currentNode.getY();
        position = new Position(x, y);
    }

    @Override
    public void setKilledListener(KilledListener listener) {
        this.killedListener = listener;
    }

    @Override
    public void setGoalListener(GoalListener listener) {
        this.goalListener = listener;
    }

    @Override
    public void update(double dt) {
        Node next;
        try {
            next = currentNode.getNext();
        } catch (NoSuchElementException e) {
            if (currentNode.isGoal()) {
                if (goalListener != null) {
                    goalListener.onGoal(this);
                }
            } else {
                // invalid path, won't move
            }
            return;
        }
        double angle = position.angle(new Position(next.getX(), next.getY()));
        position.setX(nextX(angle, dt));
        position.setY(nextY(angle, dt));

        setNextNode(angle);
    }

    private void setNextNode(double lastAngle) {
        Node next = currentNode.getNext();
        double newAngle = position.angle(
                new Position(next.getX(), next.getY()));

        if ((lastAngle - newAngle) > 0.001) {
            currentNode = next;
            if (currentNode.isGoal()) {
                if (goalListener != null) {
                    goalListener.onGoal(this);
                }
            }
        }
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
        if (health <= 0 && killedListener != null) {
            killedListener.onKilled(this);
        }
    }

    @Override
    public Stats getStats() {
        return STATS;
    }

    private double nextX(double angle, double dt) {
        return position.getX() + Math.cos(angle)*STATS.getSpeed()*dt;
    }

    private double nextY(double angle, double dt) {
        return position.getY() + Math.sin(angle)*STATS.getSpeed()*dt;
    }
}
