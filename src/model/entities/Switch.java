/*
 * File: Switch.java
 * Author: Fredrik Johansson
 * Date: 2016-12-14
 */
package model.entities;

import model.level.Position;

public class Switch implements Entity {

    private Position position;
    private String[] filepath = new String[] {"/images/switch_on.png",
                                              "/images/switch_off.png"};
    private int imageIndex = 0;
    private Node switchNode;

    public Switch(Node switchNode) {
        if (switchNode.getSuccessors().size() < 1) {
            throw new IllegalArgumentException("Node must be able to switch");
        }
        setPosition(switchNode.getPosition());
        this.switchNode = switchNode;
    }

    @Override
    public void update(double dt) {

    }

    @Override
    public void interact() {
        imageIndex = (imageIndex + 1) % 2;
        switchNode.switchSuccessor();
    }

    @Override
    public void setPosition(Position position) {
        this.position = position.clone();
    }

    @Override
    public Position getPosition() {
        return position;
    }

    @Override
    public String getFilePath() {
        return filepath[imageIndex];
    }
}
