package model.level;


import model.entities.Node;
import model.entities.Path;
import model.level.Level;
import model.level.LevelReader;
import model.level.ParseResult;
import org.junit.Test;

import java.util.HashMap;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Author: Linus Lagerhjelm
 * File: LevelReaderTest
 * Created: 16-11-28
 * Description: Test cases for the LevelReader class.
 */
public class LevelReaderTest {
    private final String FILE_NAME = "level.xml";
    private final String SCHEMA_NAME = "level_schema.xsd";

    @Test
    public void testNoNullFields() {
        LevelReader reader = new LevelReader(FILE_NAME, SCHEMA_NAME, new ParseResult() {
            @Override
            public void onSuccess(List<Level> levels) {
                Level level = levels.get(0);
                assertNotNull(level.getPads());
                assertNotNull(level.getTowers());
                assertNotNull(level.getTowerZones());
            }

            @Override
            public void onError(Exception e) {
                fail();
            }
        });
        reader.run();
    }

    @Test
    public void testCorrectLevelCount() {
        LevelReader reader = new LevelReader(FILE_NAME, SCHEMA_NAME, new ParseResult() {
            @Override
            public void onSuccess(List<Level> levels) {
                assertEquals(levels.size(), 1);
            }

            @Override
            public void onError(Exception e) {
                fail();
            }
        });
        reader.run();
    }

    @Test
    public void testCorrectTowerCount() {
        LevelReader reader = new LevelReader(FILE_NAME, SCHEMA_NAME, new ParseResult() {
            @Override
            public void onSuccess(List<Level> levels) {
                assertEquals(levels.get(0).getTowers().size(), 8);
            }

            @Override
            public void onError(Exception e) {
                fail();
            }
        });
        reader.run();
    }

    @Test
    public void testCorrectTowerPlacementZoneCount() {
        LevelReader reader = new LevelReader(FILE_NAME, SCHEMA_NAME, new ParseResult() {
            @Override
            public void onSuccess(List<Level> levels) {
                assertEquals(levels.get(0).getTowerZones().size(), 4);
            }

            @Override
            public void onError(Exception e) {
                fail();
            }
        });
        reader.run();
    }

    @Test
    public void testCorrectPadCount() {
        LevelReader reader = new LevelReader(FILE_NAME, SCHEMA_NAME, new ParseResult() {
            @Override
            public void onSuccess(List<Level> levels) {
                assertEquals(levels.get(0).getPads().size(), 2);
            }

            @Override
            public void onError(Exception e) {
                fail();
            }
        });
        reader.run();
    }

    @Test
    public void testCorrectPath() {
        final Path PATH = setUpPath();
        LevelReader reader = new LevelReader(FILE_NAME, SCHEMA_NAME, new ParseResult() {
            @Override
            public void onSuccess(List<Level> levels) {
                assertEquals(PATH, levels.get(0).getPath());
            }

            @Override
            public void onError(Exception e) {
                fail();
            }
        });
        reader.run();
    }

    private Path setUpPath() {
        Path p = new Path();
        Node n1 = new Node(1, 8, 4);
        Node n2 = new Node(2, 9, 4);
        Node n3 = new Node(3, 10, 5);
        n1.setStart();
        n2.setGoal();
        n3.setGoal();
        n1.addSuccessor(n2);
        n1.addSuccessor(n3);
        HashMap<Integer, Node> hm = new HashMap<>();
        hm.put(n1.getId(), n1);
        hm.put(n2.getId(), n2);
        hm.put(n3.getId(), n3);
        p.addNodes(hm);
        p.isValid();
        return p;
    }
}