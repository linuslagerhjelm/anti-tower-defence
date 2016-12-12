package model.entities;

import model.level.Position;

import java.util.*;

/**
 * Author: Linus Lagerhjelm
 * File: Path
 * Created: 16-11-28
 * Description: Represents all available paths on the board as a series of Nodes
 */
public class Path {
    private boolean isValid = true;
    private boolean isValidated = false;
    private Node start;
    private List<Node> goal = new ArrayList<>();

    private HashMap<Integer, Node> nodes;

    /**
     * Validates the path to make sure that it complies with the definition of
     * the path. A path is considered valid so long as every node has a
     * preceding node and there are at least one start and one goal node.
     * @return true/false if the path is valid
     */
    public boolean isValid() {
        return isValidated ? isValid : validate();
    }

    /**
     * Since the validation of the path is a quite expensive operation, we
     * perform the actual validation in a separate function and then store the
     * result as a field so that it will only have to be performed once for
     * each path.
     * @return true/false if valid
     */
    private boolean validate() {
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

        // AND at least one start
        isValid = isValid && (start != null);

        // AND at least one end
        isValid = isValid && (goal.size() > 0);

        return isValid;
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

    public void switchOnNode(Position position) {
        nodes.forEach((id, node) -> {
        });
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

    /**
     * Check if the provided node is a goal node
     * @param node the node to check
     * @return true/false if node was goal
     */
    public boolean isGoal(Node node) {
        return goal.contains(node);
    }

    /* Intellij generated methods */
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

    @Override
    public int hashCode() {
        int result = (isValid ? 1 : 0);
        result = 31 * result + (isValidated ? 1 : 0);
        result = 31 * result + start.hashCode();
        result = 31 * result + goal.hashCode();
        result = 31 * result + nodes.hashCode();
        return result;
    }

    public void resetSwitches() {

    }
}
