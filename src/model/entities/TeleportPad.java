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
    private int height;
    private int width;

    public TeleportPad(){}
    public TeleportPad(int x, int y, int width, int height) {
        setProperties(x, y, width, height);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setProperties(int x, int y, int width, int height) {
        position = new Position((double)x, (double)y);
        this.height = height;
        this.width = width;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void landOn(Troupe troupe) {
        if (target != null) {
            troupe.setPosition(position);
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
