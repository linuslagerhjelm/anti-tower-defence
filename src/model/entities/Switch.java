/*
 * File: Switch.java
 * Author: Fredrik Johansson
 * Date: 2016-12-14
 */
package model.entities;

import model.level.Position;

/**
 * A switch will have a direct connection to a node and when clicked
 * on, will change which successor the node is linked to
 */
public class Switch implements Entity {

    private Position position;
    private String[] filepath = new String[] {"/images/switch_on.png",
                                              "/images/switch_off.png"};
    private int imageIndex = 0;
    private Node switchNode;

    /**
     * A switch, linked to a specific node
     * @param switchNode Node to interact with
     */
    public Switch(Node switchNode) {
        if (switchNode.getSuccessors().size() < 1) {
            throw new IllegalArgumentException("Node must be able to switch");
        }
        setPosition(switchNode.getPosition());
        this.switchNode = switchNode;
    }

    /**
     * {@inheritDoc}
     *
     * This does nothing on a switch
     */
    @Override
    public void update(double dt) { }

    /**
     * Will change which successor the corresponding node will link to
     */
    @Override
    public void interact() {
        imageIndex = (imageIndex + 1) % 2;
        switchNode.switchSuccessor();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setPosition(Position position) {
        this.position = position.clone();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Position getPosition() {
        return position;
    }

    /**
     * {@inheritDoc}
     *
     * Will change image path after a {@link #interact} have been called
     */
    @Override
    public String getFilePath() {
        return filepath[imageIndex];
    }
}
