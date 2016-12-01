package highscore;

import java.util.List;

/**
 * Author: Linus Lagerhjelm
 * File: DatabaseResult
 * Created: 2016-12-01
 * Description: Callback interface to receive the result of a get request to
 * the database server.
 */
public interface DatabaseResult {


    /**
     * Callback method invoked with a list containing high scores from the
     * server. If no scores were available, list will be empty.
     * @param res list of available high scores
     */
    void receiveResult(List<HighScore> res);
}
