/*
 * File: LargeTower.java
 * Author: Fredrik Johansson
 * Date: 2016-12-09
 */
package model.entities.tower;

import model.entities.troupe.Troupe;

/**
 * A large tower is slower between shots but creates more damage
 */
public class LargeTower extends Tower {

    public static final TowerStats STATS = new TowerStats(20, 50, 1100);
    private String filePath = "/images/towers/darlek_red.png";

    /**
     * {@inheritDoc}
     *
     * Have no effect
     */
    @Override
    public void update(double dt) {}

    /**
     * {@inheritDoc}
     */
    @Override
    public String getFilePath() {
        return filePath;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void inRange(Troupe troupe) {
        if (canShoot()) {
            troupe.receiveDamage(STATS.getDamage());
            haveShot(STATS.getCooldown(), troupe);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TowerStats getStats() {
        return STATS;
    }
}
