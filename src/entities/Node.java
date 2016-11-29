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
    List<Node> successors = new ArrayList<>();

    public Node(int id, int x, int y) {
        this.id = id;
    }

    public void setStart() {}
    public void addSuccessor(Node successor) {
        successors.add(successor);
    }

    public Integer getId() {
        return id;
    }
}
