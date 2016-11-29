package level;

import java.util.List;

/**
 * Author: Linus Lagerhjelm
 * File: ParseResult
 * Created: 16-11-28
 * Description: Interface representing a callback to invoke when parsing
 * was finished
 */
public interface ParseResult {

    /**
     * Event handler that will receive the result of parsing an XML-file.
     * If everything were successful, levels will be a list of all the levels
     * that were read from the file. If an error occurred while reading, levels
     * will be null.
     * @param levels list of parsed level objects
     */
    void receiveResult(List<Level> levels);


}
