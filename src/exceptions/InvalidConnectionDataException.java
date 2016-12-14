package exceptions;

/**
 * Author: Linus Lagerhjelm
 * File: InvalidConnectionDataException
 * Created: 16-11-23
 * Description: Thrown when there were a problem with the data representing
 * a database connection
 */
public class InvalidConnectionDataException extends Exception {
    public InvalidConnectionDataException() {}
    public InvalidConnectionDataException(String msg) {
        super(msg);
    }
}
