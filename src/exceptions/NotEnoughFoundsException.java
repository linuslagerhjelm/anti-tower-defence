/*
 * File: NotEnoughFounds.java
 * Author: Fredrik Johansson
 * Date: 2016-11-30
 */
package exceptions;

public class NotEnoughFoundsException extends Exception {
    public NotEnoughFoundsException() {
        super();
    }

    public NotEnoughFoundsException(String message) {
        super(message);
    }

    public NotEnoughFoundsException(String message, Throwable cause) {
        super(message, cause);
    }

    public NotEnoughFoundsException(Throwable cause) {
        super(cause);
    }
}
