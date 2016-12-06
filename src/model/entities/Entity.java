/*
 * File: Entity.java
 * Author: Fredrik Johansson
 * Date: 2016-11-28
 */
package model.entities;

import model.level.Position;

public interface Entity {

    void update(double dt);
    void interact();
    void setPosition(Position position);
    Position getPosition();
}
