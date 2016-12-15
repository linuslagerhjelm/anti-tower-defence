package exceptions;

/**
 * Author: Linus Lagerhjelm
 * File: UnableToRegisterEventException
 * Created: 2016-12-06
 * Description: A event was not able to be registered
 */
public class UnableToRegisterEventException extends Exception {
    public UnableToRegisterEventException() {}
    public UnableToRegisterEventException(String msg) {
        super (msg);
    }
}
