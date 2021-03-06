package model.entities;

import exceptions.InvalidPathException;
import model.level.Position;

import java.util.*;

/**
 * Author: Linus Lagerhjelm & Fredrik Johansson
 * File: Path
 * Created: 16-11-28
 * Description: Represents all available paths on the board as a series of Nodes
 *              Will make a tree in which each branching node have only one
 *              immediate linked node, but can however be switched
 */
public class Path {
    private boolean isValid = true;
    private boolean isValidated = false;
    private Node start;
    private List<Node> goal = new ArrayList<>();

    private HashMap<Integer, Node> nodes;
    private List<Switch> switches = new ArrayList<>();

    /**
     * Validates the path to make sure that it complies with the definition of
     * the path. A path is considered valid so long as every node has a
     * preceding node and there are at least one start and one goal node.
     * @return true/false if the path is valid
     */
    public boolean isValid() throws InvalidPathException {
        return isValidated ? isValid : validate();
    }

    /**
     * Since the validation of the path is a quite expensive operation, we
     * perform the actual validation in a separate function and then store the
     * result as a field so that it will only have to be performed once for
     * each path.
     * @return true/false if valid
     */
    private boolean validate() throws InvalidPathException {
        Set<Node> visited = new HashSet<>();
        LinkedList<Node> walkNodes = new LinkedList<>(nodes.values());

        walkNodes.forEach(node -> {
            if (node.isStart()) {
                start = node;
                visited.add(node);
            }
            if (node.isGoal()) goal.add(node);
            walk(node.getSuccessors(), visited);
        });

        // If all nodes has been visited
        isValid = visited.containsAll(nodes.values());
        if (!isValid) {
            throw new InvalidPathException(
                    "Path invalid: Every node can't be visited");
        }

        // AND at least one start
        isValid = isValid && (start != null);
        if (!isValid) {
            throw new InvalidPathException("Path invalid: no start node");
        }

        // AND at least one end
        isValid = isValid && (goal.size() > 0);
        if (!isValid) {
            throw new InvalidPathException("Path invalid: no goal node");
        }

        calculateNodeLengthToStart(start, 0);

        return isValid;
    }

    /**
     * Calculate how long each node has to the start. Will traverse the
     * whole path if the start node is used as parameter.
     * @param root Node to start traversing from, should be the start node
     * @param currentLength The length to root node has to the start node,
     *                      should be zero if root node is start node
     */
    private void calculateNodeLengthToStart(Node root, double currentLength) {
        root.setLengthToStart(currentLength);

        if (!root.isGoal()) {
            for (Node successor : root.getSuccessors()) {
                double newLength = currentLength +
                        root.getPosition().lengthTo(successor.getPosition());
                calculateNodeLengthToStart(successor, newLength);
            }
        }
    }

    /**
     * Recursively walks the successor list of a node and sets each new node as
     * visited.
     * @param walkNodes the succeeding nodes
     * @param visited set containing all the visited nodes
     */
    private void walk(List<Node> walkNodes, Set<Node> visited) {
        if (walkNodes.size() == 0) {
            return;
        }
        if (visited.contains(walkNodes.get(0))) {
            isValid = false;
            return;
        }
        Node current = walkNodes.get(0);
        visited.add(current);

        if (current.getSuccessors().size() > 1) {
            switches.add(new Switch(current));
        }

        walk(walkNodes.subList(1, walkNodes.size()), visited);
    }

    /**
     * Get a nodes successor
     * @param currentNode Current node
     * @return The successor to the current node
     */
    public Node getNext(Node currentNode) {
        return currentNode.getNext();
    }

    public Node getNext(int currentNodeID) {
        Node current = nodes.get(currentNodeID);
        if (current == null) {
            throw new NoSuchElementException(
                    "No such Node with id: "+currentNodeID);
        }
        return getNext(current);
    }

    /**
     * Adds the nodes to the path as a HashMap with node id:s as key
     * @param nodes the nodes on the path
     * @return the object instance to allow method chaining
     */
    public Path addNodes(HashMap<Integer, Node> nodes) {
        this.nodes = nodes;
        return this;
    }

    /**
     * Get all the start nodes on the path
     * @return List of start nodes
     */
    public Node getStartNode() {
        return start;
    }

    public Collection<Switch> getSwitches() {
        return switches;
    }

    /**
     * Check if the provided node is a goal node
     * @param node the node to check
     * @return true/false if node was goal
     */
    public boolean isGoal(Node node) {
        return goal.contains(node);
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

        Path path = (Path) o;

        if (isValid != path.isValid) return false;
        if (isValidated != path.isValidated) return false;
        if (!start.equals(path.start)) return false;
        return goal.equals(path.goal) && nodes.equals(path.nodes);

    }

    /**
     * {@inheritDoc}
     *
     * Autogenerated by IntelliJ 2016.2
     */
    @Override
    public int hashCode() {
        int result = (isValid ? 1 : 0);
        result = 31 * result + (isValidated ? 1 : 0);
        result = 31 * result + start.hashCode();
        result = 31 * result + goal.hashCode();
        result = 31 * result + nodes.hashCode();
        return result;
    }


    /**
     * Get node which is closest to given position. Time complexity O(n).
     * @param position Position which should be examined
     * @return Closest node to position
     */
    public Node getNextNodeFrom(Position position) {
        Node closest = start;
        double shortestLength = position.lengthTo(start.getPosition());
        Stack<Node> nodeStack = new Stack<>();
        nodeStack.add(start);

        // Depth first
        while (!nodeStack.isEmpty()) {
            Node node = nodeStack.pop();
            nodeStack.addAll(node.getSuccessors());

            double length = position.lengthTo(node.getPosition());
            if (length < shortestLength) {
                shortestLength = length;
                closest = node;
            }
        }

        return closest;
    }
}
