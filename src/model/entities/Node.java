package model.entities;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * Author: Linus Lagerhjelm
 * File: Node
 * Created: 16-11-28
 * Description:
 */
public class Node {
    private Integer id;
    private Integer x;
    private Integer y;
    private boolean start = false;
    private boolean goal = false;
    private List<Node> successors = new ArrayList<>();
    private Node nextNode;

    public Node(int id, int x, int y) {
        this.id = id;
        this.x = x;
        this.y = y;
    }

    public void setStart() {
        start = true;
    }

    public void setGoal() {
        goal = true;
    }

    public void addSuccessor(Node successor) {
        successors.add(successor);
        if (nextNode == null) {
            nextNode = successor;
        }
    }

    public Integer getId() {
        return id;
    }

    public List<Node> getSuccessors() {
        return successors;
    }

    public Node getNext() {
        if (nextNode == null) {
            throw new NoSuchElementException("Node have no successors");
        }
        return nextNode;
    }

    public void switchSuccessor() {
        int currentNextIndex = successors.indexOf(nextNode);
        int switchNextIndex = currentNextIndex+1 % successors.size();
        nextNode = successors.get(switchNextIndex);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Node node = (Node) o;

        return id.equals(node.id) && successors.equals(node.successors);

    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + successors.hashCode();
        return result;
    }

    public boolean isStart() {
        return start;
    }

    public boolean isGoal() {
        return goal;
    }

    public Integer getX() {
        return x;
    }

    public Integer getY() {
        return y;
    }
}
