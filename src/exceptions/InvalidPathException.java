/*
 * File: InvalidPathException.java
 * Author: Fredrik Johansson
 * Date: 2016-12-14
 */
package exceptions;

public class InvalidPathException extends Exception {
    public InvalidPathException() {
        super();
    }

    public InvalidPathException(String message) {
        super(message);
    }

    public InvalidPathException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidPathException(Throwable cause) {
        super(cause);
    }
}
