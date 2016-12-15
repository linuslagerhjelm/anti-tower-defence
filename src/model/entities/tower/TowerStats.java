/*
 * File: TowerStats.java
 * Author: Fredrik Johansson
 * Date: 2016-12-08
 */
package model.entities.tower;

import model.entities.Stats;

/**
 * Includes the stats of a tower: damage, range and cooldown.
 */
public class TowerStats implements Stats {

    private double range;
    private int damage;
    private long cooldown;

    /**
     * Sets the stats
     * @param damage How much damage a tower makes on a troupe on one shot
     * @param range How long a towers shots reaches
     * @param cooldown How much time must a tower wait until it can shoot again
     */
    public TowerStats(int damage, double range, long cooldown) {
        this.range = range;
        this.damage = damage;
        this.cooldown = cooldown;
    }

    public double getRange() {
        return range;
    }

    public void setRange(double range) {
        this.range = range;
    }

    public int getDamage() {
        return damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public long getCooldown() {
        return cooldown;
    }

    public void setCooldown(long cooldown) {
        this.cooldown = cooldown;
    }
}
