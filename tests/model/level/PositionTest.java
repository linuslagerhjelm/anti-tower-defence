/*
 * File: PositionTest.java
 * Author: Fredrik Johansson
 * Date: 2016-12-01
 */
package model.level;

import org.junit.Test;

import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class PositionTest {

    @Test
    public void shouldGetXPosition() {
        Position pos = new Position(1, 2);
        assertEquals(1.0, pos.getX());
    }

    @Test
    public void shouldGetYPosition() {
        Position pos = new Position(1, 2);
        assertEquals(2.0, pos.getY());
    }

    @Test
    public void shouldSetXYInConstructor() {
        Position pos = new Position(1, 2);
        assertEquals(1.0, pos.getX());
        assertEquals(2.0, pos.getY());
    }

    @Test
    public void shouldSetXPosition() {
        Position pos = new Position(1, 2);
        pos.setX(3);
        assertEquals(3.0, pos.getX());
    }

    @Test
    public void shouldSetYPosition() {
        Position pos = new Position(1, 2);
        pos.setY(3);
        assertEquals(3.0, pos.getY());
    }

    @Test
    public void shouldEquals() {
        Position pos1 = new Position(1, 2);
        Position pos2 = new Position(1, 2);

        assertEquals(pos1, pos2);
    }

    @Test
    public void shouldNotEquals() {
        Position pos1 = new Position(1, 2);
        Position pos2 = new Position(3, 2);

        assertNotEquals(pos1, pos2);
    }

    @Test
    public void shouldHaveSameHash() {
        Position pos1 = new Position(1, 2);
        Position pos2 = new Position(1, 2);

        assertEquals(pos1.hashCode(), pos2.hashCode());
    }

    @Test
    public void shouldNotHaveSameHash() {
        Position pos1 = new Position(1, 2);
        Position pos2 = new Position(2, 2);

        assertNotEquals(pos1.hashCode(), pos2.hashCode());
    }
}