package model.level;

import exceptions.NoSuchPadException;
import model.entities.*;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Author: Linus Lagerhjelm
 * File: LevelXMLHandler
 * Created: 16-11-27
 * Description: Responsible for parsing XML - files into controller.game objects
 */
public class LevelXMLHandler extends DefaultHandler {
    private List<Level> levels;
    private ParseResult callback;

    /* Temporary variables used while parsing */
    private Level tmpLevel;
    private Path path;
    private Pad tmpPad;
    private Node tmpNode;
    private HashMap<Integer, Node> nodes = new HashMap<>();
    private HashMap<Integer, List<Integer>> successorMap = new HashMap<>();
    private List<TowerZone> towerZones;
    private List<Tower> towers;
    private List<Pad> pads;

    public LevelXMLHandler(ParseResult callback) {
        this.callback = callback;
    }

    @Override
    public void startElement(String uri, String localName, String qName,
                             Attributes attributes) throws SAXException {

        if (qName.equalsIgnoreCase("levels")) {
            levels = new ArrayList<>();

        } else if (qName.equalsIgnoreCase("level")) {
            tmpLevel = new Level(attributes.getValue("name"),
                    Integer.parseInt(attributes.getValue("height")),
                    Integer.parseInt(attributes.getValue("width")));


        } else if(qName.equalsIgnoreCase("rectangle")) {
            if (towerZones != null) {
                towerZones.add(new TowerZone(
                        Integer.parseInt(attributes.getValue("x")),
                        Integer.parseInt(attributes.getValue("y")),
                        Integer.parseInt(attributes.getValue("width")),
                        Integer.parseInt(attributes.getValue("height"))));

            } else if (tmpPad != null) {
                tmpPad.setProperties(
                        Integer.parseInt(attributes.getValue("x")),
                        Integer.parseInt(attributes.getValue("y")),
                        Integer.parseInt(attributes.getValue("width")),
                        Integer.parseInt(attributes.getValue("height")));
            }

        } else if(qName.equalsIgnoreCase("successor")) {
            successorMap.get(tmpNode.getId()).add(
                    Integer.parseInt(attributes.getValue("refid")));

        } else if(qName.equalsIgnoreCase("node")) {
            int id = Integer.parseInt(attributes.getValue("id"));
            tmpNode = new Node(id,
                    Integer.parseInt(attributes.getValue("x")),
                    Integer.parseInt(attributes.getValue("y")));
            nodes.put(id, tmpNode);
            successorMap.put(id, new ArrayList<>());

            if (attributes.getValue("start") != null) {
                tmpNode.setStart();
            }
            if (attributes.getValue("goal") != null) {
                tmpNode.setGoal();
            }

        } else if(qName.equalsIgnoreCase("path")) {
            path = new Path();

        } else if(qName.equalsIgnoreCase("tower")) {
            for (int i = 0; i < Integer.parseInt(attributes.getValue("count")); ++i) {
                towers.add(TowerFactory.newInstance(attributes.getValue("className")));
            }

        } else if(qName.equalsIgnoreCase("pad")) {
            try {
                tmpPad = PadFactory.newInstance(attributes.getValue("className"));

            } catch (NoSuchPadException ignore) {
                // Just don't create pad if we couldn't find one
            }

        } else if(qName.equalsIgnoreCase("towerzones")) {
            towerZones = new ArrayList<>();

        } else if(qName.equalsIgnoreCase("towers")) {
            towers = new ArrayList<>();

        } else if(qName.equalsIgnoreCase("pads")) {
            pads = new ArrayList<>();
        }
    }


    @Override
    public void endElement(String uri, String localName, String qName)
            throws SAXException {

        if (qName.equalsIgnoreCase("level")) {
            levels.add(tmpLevel);

        } else if (qName.equalsIgnoreCase("path")) {
            createPath();
            if (path.isValid()) {
                tmpLevel.addPath(path);
            }

        } else if (qName.equalsIgnoreCase("node")) {
            tmpNode = null;

        } else if (qName.equalsIgnoreCase("towerzones")) {
            tmpLevel.addTowerZones(towerZones);
            towerZones = null;

        } else if (qName.equalsIgnoreCase("towers")) {
            tmpLevel.addTowers(towers);

        } else if (qName.equalsIgnoreCase("pad")) {
            pads.add(tmpPad);

        } else if (qName.equalsIgnoreCase("pads")) {
            tmpLevel.addPads(pads);
            pads = null;

        } else if (qName.equalsIgnoreCase("levels")) {
            callback.onSuccess(levels);
        }
    }

    /**
     * Adds all the successor nodes to every node and inserts the node map into
     * the path object
     */
    private void createPath() {
        nodes.forEach((id, node) -> successorMap.get(id).forEach(successorId ->
                node.addSuccessor(nodes.get(successorId))));
        path.addNodes(nodes);
    }

}
