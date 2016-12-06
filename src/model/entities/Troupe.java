/*
 * File: Troupe.java
 * Author: Fredrik Johansson
 * Date: 2016-11-28
 */
package model.entities;

public interface Troupe extends Entity {

    interface KilledListener {
        void onKilled(Troupe troupe);
    }

    void setPath(Path path);
    void setKilledListener(KilledListener listener);
    void receiveDamage(int damage);
    Stats getStats();
}
