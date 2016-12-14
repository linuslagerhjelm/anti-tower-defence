/*
 * File: Stats.java
 * Author: Fredrik Johansson
 * Date: 2016-12-01
 */
package model.entities.troupe;

import model.entities.Stats;

public class TroupeStats implements Stats {

    private int speed;
    private int health;
    private String description;
    private String title;
    private String imgPath;

    public TroupeStats(int speed, int health, String desc, String title, String path) {
        this.speed = speed;
        this.health = health;
        this.description = desc;
        this.title = title;
        this.imgPath = path;

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImgPath() {
        return imgPath;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }
}
