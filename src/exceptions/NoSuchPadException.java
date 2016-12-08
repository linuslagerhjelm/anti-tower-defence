package exceptions;

/**
 * Author: Linus Lagerhjelm
 * File: NoSuchPadException
 * Created: 2016-12-07
 * Description: Thrown when trying to instantiate a pad via reflection that
 * does not exist
 */
public class NoSuchPadException extends Exception {
    public NoSuchPadException() {}
    public NoSuchPadException(String msg) {
        super(msg);
    }
}
