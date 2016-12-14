/*
 * File: SmallTower.java
 * Author: Fredrik Johansson
 * Date: 2016-11-28
 */
package model.entities.tower;

import model.entities.troupe.Troupe;

public class SmallTower extends Tower {

    public static final TowerStats STATS = new TowerStats(4, 100, 500);
    private String imagePath = "/images/towers/darlek.png";

    @Override
    public void update(double dt) {

    }

    @Override
    public String getFilePath() {
        return imagePath;
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
