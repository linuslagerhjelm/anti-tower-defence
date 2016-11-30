package level;

import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Author: Linus Lagerhjelm
 * File: LevelReaderTest
 * Created: 16-11-28
 * Description: Test cases for the LevelReader class
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
}