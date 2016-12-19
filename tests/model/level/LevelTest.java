/*
 * File: LevelTest.java
 * Author: Fredrik Johansson & Linus Lagerhjelm
 * Date: 2016-12-08
 */
package model.level;

import model.entities.Node;
import model.entities.Path;
import model.entities.TeleportPad;
import model.entities.troupe.Troupe;
import model.entities.troupe.TroupeFactory;
import model.player.Payment;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import static junit.framework.TestCase.*;


public class LevelTest {

    private static Level level;
    private static Troupe t;
    private static Node n1;

    @Before
    public void setup() throws Exception {
        n1 = new Node(1, 1, 1);
        Node n2 = new Node(2, 2, 2);
        n1.addSuccessor(n2);
        n2.setGoal();
        t = TroupeFactory.buyTroupe(new Payment(TroupeFactory.getCost("Wizard")), "Wizard");
        t.setStartNode(n1);
        level = new Level("Level 1", 100, 100, 100, "/images/levels/level1.png");
        level.addPath(setUpPath());
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
        level.update(1000);
        troupes.remove(t);
        assertEquals(troupes, level.getTroupes());
    }

    @Test
    public void shouldBeInSquare() {
        TeleportPad pad = new TeleportPad(1, 1, 3, 3);
        Troupe troupe = t;
        troupe.setPosition(new Position(2,2));
        assertTrue(level.onPad(pad, troupe));
    }

    @Test
    public void shouldNotBeInSquare() {
        TeleportPad pad = new TeleportPad(1, 2, 3, 4);
        Troupe troupe = t;
        troupe.setPosition(new Position(6,3));
        assertFalse(level.onPad(pad, troupe));
    }

    private Path setUpPath() throws Exception {
        Path p = new Path();
        Node n1 = new Node(1, 0, 0);
        Node n2 = new Node(2, 200, 200);
        n1.setStart();
        n2.setGoal();
        n1.addSuccessor(n2);
        HashMap<Integer, Node> hm = new HashMap<>();
        hm.put(n1.getId(), n1);
        hm.put(n2.getId(), n2);
        p.addNodes(hm);
        p.isValid();
        return p;
    }
}