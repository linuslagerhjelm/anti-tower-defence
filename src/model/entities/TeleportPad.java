package model.entities;

import model.entities.troupe.Troupe;
import model.level.Position;

/**
 * Author: Linus Lagerhjelm
 * File: TeleportPad
 * Created: 16-11-28
 * Description: Describes a pad that, when walked on, will move troupes to
 * another teleport pad that is connected with this one.
 */
public class TeleportPad implements Pad {
    private TeleportPad target;
    private Position position;
    private Node nextNode;
    private int height;
    private int width;
    private static final String filePath = "/images/pads/portal.png";

    /**
     * Empty constructor. {@link #setProperties} must be used before pad
     * is used in any other way.
     */
    public TeleportPad() { }

    /**
     * Creates a TeleportPad based on properties
     * @param x X position
     * @param y Y position
     * @param width Width of pad
     * @param height Height of pad
     */
    public TeleportPad(double x, double y, int width, int height) {
        setProperties(x, y, width, height);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setProperties(double x, double y, int width, int height) {
        position = new Position(x, y);
        this.height = height;
        this.width = width;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void landOn(Troupe troupe) {
        if (target != null) {
            target.teleportTroupe(troupe);
        }
    }

    /**
     * Connects this TeleportPad with another so that troupes can be
     * teleported there.
     * @param target
     */
    public void setTarget(TeleportPad target) {
        this.target = target;
    }

    /**
     * Set closest node to the pads position.
     * @param next Closest node
     */
    public void setNextNode(Node next) {
        nextNode = next;
    }

    /**
     * Will teleport a troupe to target pad
     * @param troupe Troupe to teleport
     */
    public void teleportTroupe(Troupe troupe) {
        troupe.setPosition(getPosition());
        if (nextNode != null) {
            troupe.setNextNode(nextNode);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void update(double dt) {}

    /**
     * {@inheritDoc}
     */
    @Override
    public void interact() {}

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
     */
    @Override
    public String getFilePath() {
        return filePath;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getHeight() {
        return height;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getWidth() {
        return width;
    }
}
