/*
 * File: NoSuchTowerException.java
 * Author: Fredrik Johansson
 * Date: 2016-12-09
 */
package exceptions;

/**
 * Thrown when no tower is found corresponding to given information
 */
public class NoSuchTowerException extends Exception {
    public NoSuchTowerException() {
        super();
    }

    public NoSuchTowerException(String message) {
        super(message);
    }

    public NoSuchTowerException(String message, Throwable cause) {
        super(message, cause);
    }

    public NoSuchTowerException(Throwable cause) {
        super(cause);
    }
}
