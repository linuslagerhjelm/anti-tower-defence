package highscore;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Author: Linus Lagerhjelm
 * File: ScoreTest
 * Created: 16-11-23
 * Description: Test cases for the Score class
 */
public class ScoreTest {
    private final int score = 10;

    @Test
    public void testNewScore() {
        Score s = new Score(score);
        assertEquals(s.getScore(), score);
    }

    @Test
    public void testPointMismatch() {
        Score s = new Score(score);
        assertNotEquals(s.getScore(), score + 1);
    }

    @Test (expected = IllegalArgumentException.class)
    public void testNegativeScore() {
        new Score(-1);
    }
}