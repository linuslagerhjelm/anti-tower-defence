/*
 * File: LevelTest.java
 * Author: Fredrik Johansson
 * Date: 2016-12-08
 */
package model.level;

import model.entities.*;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

import static junit.framework.TestCase.*;

public class LevelTest {

    private static Level level;
    private static Troupe t;
    private static Node n1;

    @BeforeClass
    public static void setup() {
        n1 = new Node(1, 1, 1);
        Node n2 = new Node(2, 2, 2);
        n1.addSuccessor(n2);
        n2.setGoal();
        t = new TeleportTroupe();
        t.setStartNode(n1);
        level = new Level("Level 1", 100, 100);
        level.addTroupe(t);
    }

    @Test
    public void shouldRemoveTroupesWhenKilled() {
        t.receiveDamage(t.getStats().getHealth());
        Set<Troupe> troupes = new HashSet<>();
        troupes.add(t);
        assertEquals(troupes, level.getTroupes());
        level.update(0.00001);
        troupes.remove(t);
        assertEquals(troupes, level.getTroupes());
    }

    @Test
    public void shouldRemoveTroupesWhenInGoal() {
        Set<Troupe> troupes = new HashSet<>();
        troupes.add(t);
        assertEquals(troupes, level.getTroupes());
        level.update(1);
        troupes.remove(t);
        assertEquals(troupes, level.getTroupes());
    }

    @Test
    public void shouldCreateVector() {
        Position p1 = new Position(1, 2);
        Position p2 = new Position(3, 4);

        double[] expected = {2, 2};
        double[] actual = Level.createVector(p1, p2);

        assertEquals(expected[0], actual[0]);
        assertEquals(expected[1], actual[1]);
    }

    @Test
    public void shouldHaveCorrectScalarProduct() {
        double[] p1 = {1, 2};
        double[] p2 = {3, 4};
        assertEquals(11.0, Level.scalarProduct(p1, p2));
    }


    @Test
    public void shouldBeInSquare() {
        TeleportPad pad = new TeleportPad(1, 2, 3, 4);
        TeleportTroupe troupe = new TeleportTroupe();
        troupe.setPosition(new Position(2,3));
        assertTrue(level.onPad(pad, troupe));
    }

    @Test
    public void shouldNotBeInSquare() {
        TeleportPad pad = new TeleportPad(1, 2, 3, 4);
        TeleportTroupe troupe = new TeleportTroupe();
        troupe.setPosition(new Position(6,3));
        assertFalse(level.onPad(pad, troupe));
    }
}