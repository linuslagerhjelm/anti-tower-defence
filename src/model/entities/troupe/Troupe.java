/*
 * File: Troupe.java
 * Author: Fredrik Johansson
 * Date: 2016-11-28
 */
package model.entities.troupe;

import model.entities.Entity;
import model.entities.Node;

public interface Troupe extends Entity {

    interface KilledListener {
        void onKilled(Troupe troupe);
    }

    interface GoalListener {
        void onGoal(Troupe troupe);
    }

    void setStartNode(Node start);
    void setNextNode(Node next);
    void setKilledListener(KilledListener listener);
    void setGoalListener(GoalListener listener);
    void receiveDamage(int damage);
    TroupeStats getStats();
    double getLengthWalked();
}
