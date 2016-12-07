package controller.eventhandler;

/**
 * Author: Linus Lagerhjelm
 * File: Observer
 * Created: 2016-12-07
 * Description: Classes that implements this interface can register themselves
 * in any class that implements the Observable interface in order to receive
 * updates when the object has changed.
 */
public interface Observer {

    /**
     * Receives updates whenever a subject in which the implementor of this
     * interface is registered has changed
     * @param subject the observable that has changed
     * @param action the change that were made
     */
    void update(Observable subject, Object action);
}
