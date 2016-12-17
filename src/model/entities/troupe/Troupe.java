/*
 * File: Troupe.java
 * Author: Fredrik Johansson
 * Date: 2016-11-28
 */
package model.entities.troupe;

import model.entities.Entity;
import model.entities.Node;
import model.entities.Pad;

public interface Troupe extends Entity {

    /**
     * A listener for when a Troupe is killed. The troupe itself decides
     * when it dies
     */
    interface KilledListener {
        void onKilled(Troupe troupe);
    }

    /**
     * A listener for when a Troupe reaches a goal.
     */
    interface GoalListener {
        void onGoal(Troupe troupe);
    }

    /**
     * A listener for when a Troupe spawns a pad
     */
    interface PadSpawnListener {
        void onPadSpawned(Pad pad);
    }

    /**
     * Set the start position of a troupe. Should be set before update is
     * called on a troupe.
     * @param start Start position of troupe
     */
    void setStartNode(Node start);

    /**
     * Sets which node a troupe should be aiming for. Can be set at any point
     * @param next Next node for the troupe
     */
    void setNextNode(Node next);

    /**
     * Sets KilledListener for the troupe. Triggers when a Troupe dies.
     * Only when KilledListener can be set.
     * @param listener Listener for when troupe dies
     */
    void setKilledListener(KilledListener listener);

    /**
     * Sets GoalListener for the troupe. Triggers when a Troupe reaches
     * a goal. Only one GoalListener can be set.
     * @param listener Listener for when a troupe reaches a goal
     */
    void setGoalListener(GoalListener listener);

    /**
     * Sets PadSpawnListener for the troupe. Triggers when a Troupe spawnes
     * a pad. Only one PadSpawnListener can be set.
     * @param listener Listener for when a troupe spawns a pad
     */
    void setPadSpawnedListener(PadSpawnListener listener);

    /**
     * Damage which the troupe will receive. Implementation decides what
     * the result of damage will be.
     * @param damage Amount of damage to troupe
     */
    void receiveDamage(int damage);

    /**
     * Returns TroupeStats for specified Troupe, which includes speed,
     * health, etc. @see TroupeStats.
     * @return Stats for troupe
     */
    TroupeStats getStats();

    /**
     * Returns how long a troupe has traveled. In unit of length units
     * @return Length traveled
     */
    double getLengthWalked();
}
