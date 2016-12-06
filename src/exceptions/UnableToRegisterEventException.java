package exceptions;

/**
 * Author: Linus Lagerhjelm
 * File: UnableToRegisterEventException
 * Created: 2016-12-06
 * Description:
 */
public class UnableToRegisterEventException extends Exception {
    public UnableToRegisterEventException() {}
    public UnableToRegisterEventException(String msg) {
        super (msg);
    }
}
