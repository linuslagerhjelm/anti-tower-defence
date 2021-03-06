package model.level;

import exceptions.InvalidPathException;
import exceptions.NoSuchPadException;
import exceptions.NoSuchTowerException;
import model.entities.Node;
import model.entities.Pad;
import model.entities.PadFactory;
import model.entities.Path;
import model.entities.tower.Tower;
import model.entities.tower.TowerFactory;
import model.entities.tower.TowerZone;
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
    private boolean failed = false;

    public LevelXMLHandler(ParseResult callback) {
        this.callback = callback;
    }

    @Override
    public void startElement(String uri, String localName, String qName,
                             Attributes attributes) throws SAXException {

        if (failed) return;

        if (qName.equalsIgnoreCase("levels")) {
            levels = new ArrayList<>();

        } else if (qName.equalsIgnoreCase("level")) {
            tmpLevel = new Level(attributes.getValue("name"),
                    Integer.parseInt(attributes.getValue("height")),
                    Integer.parseInt(attributes.getValue("width")),
                    Integer.parseInt(attributes.getValue("troupesToWin")),
                    attributes.getValue("texture"));


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
                try {
                    towers.add(TowerFactory.newInstance(attributes.getValue("className")));
                } catch (NoSuchTowerException e) {
                    // ignore, don't create any such tower
                }
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

        if (failed) return;

        if (qName.equalsIgnoreCase("level")) {
            tmpLevel.setInitialState(tmpLevel);
            levels.add(tmpLevel.clone());

        } else if (qName.equalsIgnoreCase("path")) {
            createPath();
            try {
                if (path.isValid()) {
                    tmpLevel.addPath(path);
                } else {
                    callback.onError(new InvalidPathException("Invalid path in xml"));
                    failed = true;
                }
            } catch (InvalidPathException e) {
                callback.onError(e);
                failed = true;
            }

        } else if (qName.equalsIgnoreCase("node")) {
            tmpNode = null;

        } else if (qName.equalsIgnoreCase("towerzones")) {
            tmpLevel.addTowerZones(towerZones);
            towerZones = null;

        } else if (qName.equalsIgnoreCase("towers")) {
            tmpLevel.addTowers(towers);

        } else if (qName.equalsIgnoreCase("pad")) {
            if (tmpPad != null) {
                pads.add(tmpPad);
            }

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
