/*
 * File: TeleportTroupeTest.java
 * Author: Fredrik Johansson
 * Date: 2016-12-01
 */
package model.entities;

import model.level.Position;
import org.junit.Test;

import java.util.HashMap;
import java.util.concurrent.atomic.AtomicBoolean;

import static java.lang.Math.pow;
import static java.lang.Math.sqrt;
import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNotNull;
import static org.junit.Assert.assertNotEquals;

public class TeleportTroupeTest {

    @Test
    public void shouldDieAfterAllHealthDepleted() {
        AtomicBoolean killed = new AtomicBoolean(false);
        Troupe t = new TeleportTroupe();
        t.setKilledListener((troupe) -> killed.set(true));
        t.receiveDamage(t.getStats().getHealth());
        assertEquals(true, killed.get());
    }

    @Test
    public void shouldNotDieWhenNotEnoughDamage() {
        AtomicBoolean killed = new AtomicBoolean(false);
        Troupe t = new TeleportTroupe();
        t.setKilledListener((troupe) -> killed.set(true));
        t.receiveDamage(t.getStats().getHealth()-1);
        assertEquals(false, killed.get());
    }

    @Test
    public void shouldMoveOnUpdate() {
        Troupe t = new TeleportTroupe();
        t.setPosition(new Position(1, 2));
        t.update(1);
        assertNotNull(t.getPosition());
        assertNotEquals(new Position(1, 2), t.getPosition());
    }

    @Test
    public void shouldMoveAlongPath() {
        Troupe t = new TeleportTroupe();

        HashMap<Integer, Node> nodes = new HashMap<>();
        Node start = new Node(1, 0, 0);
        start.setStart();
        nodes.put(1, start);
        Node goal = new Node(2, 999, 999);
        goal.setGoal();
        nodes.put(2, goal);

        Path path = new Path();
        path.addNodes(nodes);
        path.isValid();
        t.setPath(path);

        double time = 1;
        t.update(time);

        assertNotNull(t.getPosition());

        Position end = pythagoras(t.getStats().getSpeed(), time);
        assertEquals(end, t.getPosition());
    }

    @Test
    public void shouldNotMoveWhenNoUpdate() {
        Troupe t = new TeleportTroupe();
        Position start = new Position(1, 2);
        t.setPosition(start);
        assertEquals(start, t.getPosition());
    }

    private Position pythagoras(int speed, double time) {
        double distance = speed*time;
        double xy = sqrt(pow(distance, 2)/2);
        return new Position(xy, xy);
    }
}