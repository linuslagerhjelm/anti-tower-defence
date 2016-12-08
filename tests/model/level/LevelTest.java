/*
 * File: LevelTest.java
 * Author: Fredrik Johansson
 * Date: 2016-12-08
 */
package model.level;

import model.entities.Node;
import model.entities.TeleportTroupe;
import model.entities.Troupe;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

import static junit.framework.TestCase.assertEquals;

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
}