/*
 * File: NodeTest.java
 * Author: Fredrik Johansson
 * Date: 2016-12-08
 */
package model.entities;

import org.junit.Test;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;

public class NodeTest {

    @Test
    public void shouldSetStart() {
        Node n = new Node(1, 1, 1);
        n.setStart();
        assertEquals(true, n.isStart());
    }

    @Test
    public void shouldSetGoal() {
        Node n = new Node(1, 1, 1);
        n.setStart();
        assertTrue(n.isStart());
    }

    @Test
    public void shouldAddSuccessor() {
        Node n1 = new Node(1, 1, 1);
        Node n2 = new Node(2, 2, 2);
        n1.addSuccessor(n2);
        assertTrue(n1.hasSuccessor());
    }

    @Test
    public void shouldHaveNext() {
        Node n1 = new Node(1, 1, 1);
        Node n2 = new Node(2, 2, 2);
        n1.addSuccessor(n2);
        assertEquals(n2, n1.getNext());
    }

    @Test
    public void shouldSwitchNext() {
        Node n1 = new Node(1, 1, 1);
        Node n2 = new Node(2, 2, 2);
        Node n3 = new Node(3, 3, 3);
        n1.addSuccessor(n2);
        n1.addSuccessor(n3);
        n1.switchSuccessor();
        assertEquals(n3, n1.getNext());
    }

    @Test
    public void shouldSwitchMultipleTimes() {
        Node n1 = new Node(1, 1, 1);
        Node n2 = new Node(2, 2, 2);
        Node n3 = new Node(3, 3, 3);
        n1.addSuccessor(n2);
        n1.addSuccessor(n3);

        n1.switchSuccessor();
        n1.switchSuccessor();
        n1.switchSuccessor();
        n1.switchSuccessor();

        assertEquals(n2, n1.getNext());
    }

}