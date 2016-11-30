package entities;

import java.util.ArrayList;
import java.util.List;

/**
 * Author: Linus Lagerhjelm
 * File: Node
 * Created: 16-11-28
 * Description:
 */
public class Node {
    Integer id;
    Integer x;
    Integer y;
    boolean start = false;
    boolean goal = false;
    List<Node> successors = new ArrayList<>();

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
    }

    public Integer getId() {
        return id;
    }

    public List<Node> getSuccessors() {
        return successors;
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
}
