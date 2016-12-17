package model.entities;

import org.junit.BeforeClass;
import org.junit.Test;

import java.util.HashMap;

import static org.junit.Assert.*;

/**
 * Author: Linus Lagerhjelm
 * File: PathTest
 * Created: 16-11-30
 * Description: Test cases for the path class
 */
public class PathTest {
    private static final Integer NODE_COUNT = 10;
    private static HashMap<Integer, Node> validNodes;
    private static HashMap<Integer, Node> invalidNodes;

    @BeforeClass
    public static void setUpValidNodes() {
        validNodes = new HashMap<>();
        for (int i = 0; i < NODE_COUNT; i++) {
            Node tmp = new Node(i, i, i);
            if (i == 0) {
                tmp.setGoal();
            } else if (i > 0) {
                tmp.addSuccessor(validNodes.get(i-1));
            }
            validNodes.put(i, tmp);
        }
        validNodes.get(NODE_COUNT - 1).setStart();
    }

    @BeforeClass
    public static void setupInvalidNodes() throws Exception {
        invalidNodes = new HashMap<>();
        for (int i = 0; i < NODE_COUNT; i++) {
            Node tmp = new Node(i, i, i);
            if (i > 0) {
                tmp.addSuccessor(invalidNodes.get(i-1));
            }
            invalidNodes.put(i, tmp);
        }
        Node invalidNode = new Node(NODE_COUNT + 2, NODE_COUNT, NODE_COUNT);
        invalidNodes.put(NODE_COUNT + 2, invalidNode);
    }

    @Test
    public void testValidPath() throws Exception {
        Path p = new Path().addNodes(validNodes);
        assertTrue(p.isValid());
    }

    @Test
    public void testInvalidPath() throws Exception {
        Path p = new Path().addNodes(invalidNodes);
        assertFalse(p.isValid());
    }
}