package model.highscore;

import org.junit.Test;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

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

    @Test
    public void testFormattedString() throws Exception {
        String dateString = "2016-12-07:20.49";
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd:HH.mm", Locale.ENGLISH);
        HighScore hs = new HighScore(score, format.parse(dateString), ID);
        assertEquals("" + ID + " 2016-12-07:20.49 " + score.getScore(), hs.getHighScoreString());
    }
}