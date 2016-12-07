package controller.eventhandler;

/**
 * Author: Linus Lagerhjelm
 * File: Observable
 * Created: 2016-12-07
 * Description: Observable interface, classes that implement this interface
 * can be observed by any class that implements the Observer interface
 */
public interface Observable {

    /**
     * Register a new observer to receive updates on changes to this object
     * @param observer the observer to register
     */
    void registerObserver(Observer observer);

    /**
     * Unregisters an observer. This observer will no longer receive updates
     * from this object when it changes.
     * @param observer the observer to unregister
     */
    void unregisterObserver(Observer observer);
}
