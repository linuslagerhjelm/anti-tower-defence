package model.entities;

import model.level.Position;

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
    private Position position;
    private boolean start = false;
    private boolean goal = false;
    private List<Node> successors = new ArrayList<>();
    private Node nextNode;

    public Node(int id, double x, double y) {
        this.id = id;
        this.position = new Position(x, y);
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

    public boolean hasSuccessor() {
        return successors.size() > 0;
    }

    public List<Node> getSuccessors() {
        return successors;
    }

    /**
     * Get the next node in which this node link to, if there is one
     * @throws NoSuchElementException No next node exist
     * @return Next node
     */
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

        if (start != node.start) return false;
        if (goal != node.goal) return false;
        if (!id.equals(node.id)) return false;
        return position != null ? position.equals(node.position) : node.position == null;

    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + (position != null ? position.hashCode() : 0);
        result = 31 * result + (start ? 1 : 0);
        result = 31 * result + (goal ? 1 : 0);
        return result;
    }

    public boolean isStart() {
        return start;
    }

    public boolean isGoal() {
        return goal;
    }

    public Position getPosition() {
        return position;
    }
}
