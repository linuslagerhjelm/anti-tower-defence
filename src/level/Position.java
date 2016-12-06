/*
 * File: Position.java
 * Author: Fredrik Johansson
 * Date: 2016-12-01
 */
package level;

/**
 * Position contains two coordinates, x and y
 */
public class Position {

    private double x;
    private double y;

    /**
     * Set initial values for x and y
     * @param x X coordinate
     * @param y Y coordinate
     */
    public Position(double x, double y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Get y coordinate
     * @return Y coordinate
     */
    public double getY() {
        return y;
    }

    /**
     * Set y coordinate
     * @param y Y coordinate
     */
    public void setY(double y) {
        this.y = y;
    }

    /**
     * Get x coordinate
     * @return X coordinate
     */
    public double getX() {
        return x;
    }

    /**
     * Set X coordinate
     * @param x X coordinate
     */
    public void setX(double x) {
        this.x = x;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Position position = (Position) o;

        if (Double.compare(position.x, x) != 0) return false;
        return Double.compare(position.y, y) == 0;

    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        temp = Double.doubleToLongBits(x);
        result = (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(y);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }
}
