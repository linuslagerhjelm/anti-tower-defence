/*
 * File: Stats.java
 * Author: Fredrik Johansson
 * Date: 2016-12-01
 */
package entities;

public class Stats {

    private int speed;
    private int health;

    public Stats(int speed, int health) {
        this.speed = speed;
        this.health = health;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }
}
