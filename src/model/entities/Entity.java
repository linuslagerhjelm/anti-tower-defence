/*
 * File: Entity.java
 * Author: Fredrik Johansson
 * Date: 2016-11-28
 */
package model.entities;

import model.level.Position;

/**
 * Entity is any object which should be draw on the screen
 */
public interface Entity {

    /**
     * Should run each turn in the game loop
     * @param dt Delta time. Time between last update
     */
    void update(double dt);


    void interact();

    /**
     * Set the position of the entity
     * @param position Position
     */
    void setPosition(Position position);

    /**
     * Get the position of the entity
     * @return Position
     */
    Position getPosition();
}
