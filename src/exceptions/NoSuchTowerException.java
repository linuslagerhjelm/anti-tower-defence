/*
 * File: NoSuchTowerException.java
 * Author: Fredrik Johansson
 * Date: 2016-12-09
 */
package exceptions;

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
