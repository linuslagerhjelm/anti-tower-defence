/*
 * File: NotEnoughFounds.java
 * Author: Fredrik Johansson
 * Date: 2016-11-30
 */
package exceptions;

public class NotEnoughFounds extends Exception {
    public NotEnoughFounds() {
        super();
    }

    public NotEnoughFounds(String message) {
        super(message);
    }

    public NotEnoughFounds(String message, Throwable cause) {
        super(message, cause);
    }

    public NotEnoughFounds(Throwable cause) {
        super(cause);
    }
}
