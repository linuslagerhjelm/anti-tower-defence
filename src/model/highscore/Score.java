package model.highscore;

/**
 * Author: Linus Lagerhjelm
 * File: Score
 * Created: 16-11-23
 * Description: Represents a score value
 */
public class Score {
    private final int score;

    /**
     * Sets the score. Must be positive.
     * @param score Score to set
     * @throws IllegalArgumentException If score is negative
     */
    public Score(int score) {
        if (score < 0) {
            throw new IllegalArgumentException("Scores can not be negative");
        }
        this.score = score;
    }

    /**
     * Get the score contained within this class
     * @return the user scores
     */
    public int getScore() {
        return score;
    }

    /* Intellij generated method */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Score score1 = (Score) o;

        return score == score1.score;

    }

    /* Intellij generated method */
    @Override
    public int hashCode() {
        return score;
    }
}
