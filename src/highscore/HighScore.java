package highscore;

import java.util.Date;

/**
 * Author: Linus Lagerhjelm
 * File: HighScore
 * Created: 16-11-23
 * Description: Represents a high score including relevant meta data. Immutable
 */
public final class HighScore {
    private final Score score;
    private final Date time;
    private final int ID;

    public HighScore(Score s, Date d, int id) {
        this.score = new Score(s.getScore());
        this.time = new Date(d.getTime());
        this.ID = id;
    }

    /**
     * Gets the score value as a score object
     * @return the score contained in the object
     */
    public Score getScore() {
        return new Score(score.getScore());
    }

    /**
     * The time when the high score were set as a timestamp
     * @return time of high score as a timestamp
     */
    public long getTime() {
        return time.getTime();
    }

    /**
     * Get the level of which this high score is set as a level ID
     * @return level ID for the high score
     */
    public int getID() {
        return ID;
    }

    /* Intellij generated method */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        HighScore highScore = (HighScore) o;

        return (ID == highScore.ID &&
                score.equals(highScore.score) &&
                time.equals(highScore.time));

    }

    /* Intellij generated method */
    @Override
    public int hashCode() {
        int result = score.hashCode();
        result = 31 * result + time.hashCode();
        result = 31 * result + ID;
        return result;
    }
}
