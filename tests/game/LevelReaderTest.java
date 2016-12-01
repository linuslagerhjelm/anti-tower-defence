package game;

import level.Level;
import level.LevelReader;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Author: Linus Lagerhjelm
 * File: LevelReaderTest
 * Created: 16-11-28
 * Description:
 */
public class LevelReaderTest {
    private final String FILE_NAME = "level.xml";

    @Test
    public void testNoNullFields() {
        LevelReader reader = new LevelReader(FILE_NAME, list -> {
            Level level = list.get(0);
            assertNotNull(level.getPads());
            assertNotNull(level.getTowers());
            assertNotNull(level.getTowerZones());
        });
        reader.run();
    }

    @Test
    public void testCorrectLevelCount() {
        LevelReader reader = new LevelReader(FILE_NAME, list -> {
            assertEquals(list.size(), 1);
        });
        reader.run();
    }

    @Test
    public void testCorrectTowerCount() {
        LevelReader reader = new LevelReader(FILE_NAME, list -> {
            assertEquals(list.get(0).getTowers().size(), 8);
        });
        reader.run();
    }

    @Test
    public void testCorrectTowerPlacementZoneCount() {
        LevelReader reader = new LevelReader(FILE_NAME, list -> {
            assertEquals(list.get(0).getTowerZones().size(), 4);
        });
        reader.run();
    }

    @Test
    public void testCorrectPadCount() {
        LevelReader reader = new LevelReader(FILE_NAME, list -> {
            assertEquals(list.get(0).getPads().size(), 2);
        });
        reader.run();
    }
}