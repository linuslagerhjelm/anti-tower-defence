package model.entities.tower;

/**
 * Author: Linus Lagerhjelm
 * File: TowerZone
 * Created: 16-11-28
 * Description:
 */
public class TowerZone {

    private final double x;
    private final double y;
    private final int width;
    private final int height;

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

    public int size() {
        return width*height;
    }
}
