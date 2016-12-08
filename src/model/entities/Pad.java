package model.entities;

import model.entities.troupe.Troupe;
import model.level.Position;

/**
 * Author: Linus Lagerhjelm
 * File: Pad
 * Created: 2016-12-08
 * Description: Describes an interface for a special pad. Classes that
 * implements this interface can be loaded via reflection in order to specify
 * special actions to take when troupes walks over it.
 */
public interface Pad {

    /**
     * Defines the position and dimension of the pad
     * @param x x-position of upper left corner of pad
     * @param y y-position of upper left corner of pad
     * @param width determines the width  of the pad
     * @param height determines the height of the pad
     */
    void setProperties(int x, int y, int width, int height);

    /**
     * Returns the position of the upper left corner of this pad. In order to
     * completely infer weather a troupe has landed on this pad, the area has
     * to be acquired via the getHeight and getWidth methods
     * @return the position of the pad
     */
    Position getPosition();

    /**
     * Returns the height of this pad
     * @return the height of the pad
     */
    int getHeight();

    /**
     * Returns the width of this pad
     * @return the width of the pad
     */
    int getWidth();

    /**
     * Tells the implementor of this interface that a troupe has landed on
     * the special area that is specified by this pad.
     * @param troupe
     */
    void landOn(Troupe troupe);

}
