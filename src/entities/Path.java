package entities;

import java.util.HashMap;

/**
 * Author: Linus Lagerhjelm
 * File: Path
 * Created: 16-11-28
 * Description:
 */
public class Path {
    HashMap<Integer, Node> nodes;

    public void addEntities() {}
    public boolean isValid() { return true; }

    public void addNodes(HashMap<Integer, Node> nodes) {
        this.nodes = nodes;
    }
}
