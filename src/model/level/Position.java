/*
 * File: Position.java
 * Author: Fredrik Johansson
 * Date: 2016-12-01
 */
package model.level;

/**
 * Position contains two coordinates, x and y
 */
public class Position implements Cloneable {

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

    /**
     * Get angle between this and another position, in radians
     * @param position2 Position to compare to
     * @return Angle in radians
     */
    public double angle(Position position2) {
        return Math.atan2(position2.getY()-y, position2.getX()-x);
    }

    /**
     * Tests if second position is withing range of given radius
     * @param position2 Position to compare with
     * @param range Radius to compare with
     * @return Whether second position is in range
     */
    public boolean inRange(Position position2, double range) {
        return lengthTo(position2) < range;
    }

    /**
     * Give euclidean length between two positions
     * @param position2 Position to compare to
     * @return Distance between the two position, always positive
     */
    public double lengthTo(Position position2) {
        return Math.sqrt(Math.pow(x-position2.getX(), 2) +
                         Math.pow(y-position2.getY(), 2));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Position clone() {
        return new Position(x, y);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Position position = (Position) o;

        if (Math.abs(position.x-x) > 0.000001) return false;
        return Math.abs(position.y-y) < 0.000001;
    }

    /**
     * {@inheritDoc}
     */
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


    @Override
    public String toString() {
        return super.toString()+"{x: "+x+", y:"+y+"}";
    }
}
