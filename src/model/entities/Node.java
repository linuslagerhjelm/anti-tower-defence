package model.entities;

import model.level.Position;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * Author: Linus Lagerhjelm
 * File: Node
 * Created: 16-11-28
 * Description: A node have a position, can be a start, goal or neither, and
 *              can have 0 or more successive nodes. A node will be linked to
 *              one of the successors a a time, but can be changed.
 */
public class Node {
    private Integer id;
    private Position position;
    private boolean start = false;
    private boolean goal = false;
    private List<Node> successors = new ArrayList<>();
    private Node nextNode;
    private double lengthToStart;

    /**
     * Creates a new nodes on the specified coordinates
     * @param id Id number of node. Must be unique
     * @param x X position of node
     * @param y Y position of node
     */
    public Node(int id, double x, double y) {
        this.id = id;
        this.position = new Position(x, y);
    }

    /**
     * Sets that this node is a start
     */
    public void setStart() {
        start = true;
    }

    /**
     * Sets that this node is a goal
     */
    public void setGoal() {
        goal = true;
    }

    /**
     * Adds successor nodes to this node, which will be a one way link
     * @param successor Successor node
     */
    public void addSuccessor(Node successor) {
        successors.add(successor);
        if (nextNode == null) {
            nextNode = successor;
        }
    }

    /**
     * Get id of node. Unique
     * @return id of node
     */
    public Integer getId() {
        return id;
    }

    /**
     * Returns if node have successor nodes of not
     * @return If there exist any successor
     */
    public boolean hasSuccessor() {
        return successors.size() > 0;
    }

    /**
     * Return a list of the successors. Will be empty if there is none
     * @return A ist of successor nodes
     */
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

    /**
     * Switch the node among the successors which is linked to. Will
     * change witch node is returned by {@link #getNext}
     */
    public void switchSuccessor() {
        int currentNextIndex = successors.indexOf(nextNode);
        int switchNextIndex = (currentNextIndex+1) % successors.size();
        nextNode = successors.get(switchNextIndex);
    }

    /**
     * {@inheritDoc}
     *
     * Autogenerated by IntelliJ 2016.2
     */
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

    /**
     * {@inheritDoc}
     *
     * Autogenerated by IntelliJ 2016.2
     */
    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + (position != null ? position.hashCode() : 0);
        result = 31 * result + (start ? 1 : 0);
        result = 31 * result + (goal ? 1 : 0);
        return result;
    }

    /**
     * Returns if node is a start or not
     * @return if node is a start or not
     */
    public boolean isStart() {
        return start;
    }

    /**
     * Returns if node is a goal or not
     * @return if node is a goal or not
     */
    public boolean isGoal() {
        return goal;
    }

    /**
     * Returns position of node
     * @return position of node
     */
    public Position getPosition() {
        return position.clone();
    }

    /**
     * Get the length this node has to the start node
     * @return Length to start node (not straight
     *                      line but along path)
     */
    public double getLengthToStart() {
        return lengthToStart;
    }

    /**
     * Set the length this node has to the start node, when walking along the
     * path
     * @param lengthToStart Length to start node (not straight
     *                      line but along path)
     */
    public void setLengthToStart(double lengthToStart) {
        this.lengthToStart = lengthToStart;
    }
}
