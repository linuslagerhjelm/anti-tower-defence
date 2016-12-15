/*
 * File: SmallTower.java
 * Author: Fredrik Johansson
 * Date: 2016-11-28
 */
package model.entities.tower;

import model.entities.troupe.Troupe;

/**
 * A small tower shoots faster but creates less damage
 */
public class SmallTower extends Tower {

    public static final TowerStats STATS = new TowerStats(10, 100, 500);
    private String imagePath = "/images/towers/darlek.png";

    /**
     * {@inheritDoc}
     *
     * Have no effect
     */
    @Override
    public void update(double dt) {

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getFilePath() {
        return imagePath;
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
