/*
 * File: Stats.java
 * Author: Fredrik Johansson
 * Date: 2016-12-01
 */
package model.entities.troupe;

import model.entities.Stats;

/**
 * Contains information about a troupe
 */
public class TroupeStats implements Stats {

    private int speed;
    private int health;
    private int cost;
    private String description;
    private String title;
    private String imgPath;

    /**
     * Sets stats for troupe
     * @param speed How fast a troupe travels
     * @param health How much health a troupe have
     * @param description A description of the troupe
     * @param title A troupes title
     * @param path Path to image which corresponds to the troupe
     */
    public TroupeStats(int speed, int health, int cost, String description,
                       String title, String path) {

        this.speed = speed;
        this.health = health;
        this.cost = cost;
        this.description = description;
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

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }
}
