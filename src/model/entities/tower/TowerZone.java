package model.entities.tower;

/**
 * Author: Linus Lagerhjelm
 * File: TowerZone
 * Created: 16-11-28
 * Description: A rectangular zone in which a tower can be placed. Only
 *              includes geometric information
 */
public class TowerZone {

    private final double x;
    private final double y;
    private final int width;
    private final int height;

    /**
     * Sets all the geometrical information
     * @param x X position of zone
     * @param y Y position of zone
     * @param width Width of zone
     * @param height Height of zone
     */
    public TowerZone(double x, double y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public double getHeight() {
        return height;
    }

    public double getWidth() {
        return width;
    }

    public double getY() {
        return y;
    }

    public double getX() {
        return x;
    }

    /**
     * Size in form of width * height
     * @return Size of area
     */
    public int size() {
        return width*height;
    }
}
