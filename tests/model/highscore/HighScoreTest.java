package model.highscore;

import org.junit.Test;
import java.util.Date;

import static org.junit.Assert.assertEquals;

/**
 * Author: Linus Lagerhjelm
 * File: HighScoreTest
 * Created: 16-11-23
 * Description: Test cases for the HighScore class
 */
public class HighScoreTest {
    private final Score score = new Score(10);
    private final Date date = new Date();
    private final int ID = 1337;

    @Test
    public void testNewHighScore() {
        new HighScore(score, date, ID);
    }

    @Test
    public void testCorrectTimeStamp() {
        HighScore hs = new HighScore(score, date, ID);
        assertEquals(hs.getTime(), date.getTime());
    }

    @Test
    public void testCorrectScore() {
        HighScore hs = new HighScore(score, date, ID);
        assertEquals(hs.getScore(), score);
    }

    @Test
    public void testCorrectId() {
        HighScore hs = new HighScore(score, date, ID);
        assertEquals(hs.getID(), ID);
    }

    @Test
    public void testEquals() {
        assertEquals(new HighScore(score, date, ID),
                new HighScore(score, date, ID));
    }
}