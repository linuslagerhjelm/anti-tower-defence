/*
 * File: LargeTower.java
 * Author: Fredrik Johansson
 * Date: 2016-12-09
 */
package model.entities.tower;

import model.entities.troupe.Troupe;

public class LargeTower extends Tower {

    public static final TowerStats STATS = new TowerStats(7, 350, 1100);

    @Override
    public void update(double dt) {

    }

    @Override
    public void inRange(Troupe troupe) {
        if (canShoot()) {
            troupe.receiveDamage(STATS.getDamage());
            haveShoot(STATS.getCooldown(), troupe);
        }
    }

    @Override
    public TowerStats getStats() {
        return STATS;
    }
}
