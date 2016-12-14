package model.entities.troupe;

import model.entities.Node;
import model.level.Position;

import java.util.NoSuchElementException;

/**
 * Author: Linus Lagerhjelm
 * File: DefaultTroupe
 * Created: 2016-12-07
 * Description: The Troupe that will be instantiated if no other
 * Troupe type were specified.
 */
public class DefaultTroupe implements Troupe {

    public static final TroupeStats STATS = new TroupeStats(100, 12,
            "Just a regular troupe", "Golem", "/images/troops/golem.png");


    private KilledListener killedListener;
    private GoalListener goalListener;
    private Position position;
    private int health = STATS.getHealth();
    private Node currentNode;
    private double walkedLength;

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
    @Override
    public void setNextNode(Node next) {
        currentNode = next;
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

    //@Override
    public void updateOld(double dt) {
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
        double angle = position.angle(next.getPosition());
        double nextX = nextX(angle, dt);
        double nextY = nextY(angle, dt);
        walkedLength += position.lengthTo(new Position(nextX, nextY));
        position.setX(nextX);
        position.setY(nextY);

        Node nextAfterNext = getNextNode(angle);
        if (nextAfterNext != null) {
            setNextNode(nextAfterNext);
        }
    }

    private Node getNextNode(double lastAngle) {
        Node next = currentNode.getNext();
        double newAngle = position.angle(next.getPosition());

        if ((lastAngle - newAngle) > 0.001) {
            if (currentNode.isGoal()) {
                if (goalListener != null) {
                    goalListener.onGoal(this);
                }
            }
            return next;
        }
        return null;
    }

    @Override
    public void interact() {

    }

    @Override
    public void setPosition(Position position) {
        this.position = position.clone();
    }

    @Override
    public Position getPosition() {
        return position;
    }

    @Override
    public String getFilePath() {
        return STATS.getImgPath();
    }

    @Override
    public void receiveDamage(int damage) {
        health -= damage;
        if (health <= 0 && killedListener != null) {
            killedListener.onKilled(this);
        }
    }

    @Override
    public TroupeStats getStats() {
        return STATS;
    }

    @Override
    public double getLengthWalked() {
        return walkedLength;
    }

    private double nextX(double angle, double dt) {
        return position.getX() + Math.cos(angle)*STATS.getSpeed()*dt;
    }

    private double nextY(double angle, double dt) {
        return position.getY() + Math.sin(angle)*STATS.getSpeed()*dt;
    }

}
