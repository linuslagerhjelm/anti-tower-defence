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
     * Invoked with the result of the parsing if everything were successful.
     * @param levels list of parsed level objects
     */
    void onSuccess(List<Level> levels);

    /**
     * Error callback that will be invoked if the parser encountered an error.
     * Invoked with the exception that were thrown
     * @param e The exception encountered while parsing
     */
    void onError(Exception e);
}
