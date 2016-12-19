package controller.eventhandler;

import controller.eventhandler.events.SystemEvent;
import exceptions.UnableToRegisterEventException;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Author: Linus Lagerhjelm
 * File: Pubsub
 * Created: 2016-12-06
 * Description: Handles synchronization between event-passing between the
 * event dispatch thread and the main thread. Every operation in this class
 * is thread safe.
 */
public class Pubsub {
    private LinkedBlockingQueue<SystemEvent> eventQ = new LinkedBlockingQueue<>();

    /**
     * Adds a system event to the event dispatch Queue
     * @param event the event to register
     * @throws UnableToRegisterEventException
     */
    public void registerEvent(SystemEvent event)
            throws UnableToRegisterEventException {

        try {
            eventQ.put(event);

        } catch (InterruptedException e) {
            throw new UnableToRegisterEventException("Interrupted operation");
        }
    }

    /**
     * Gets all the SystemEvents currently on the Queue. Calls to this method
     * will consume all the events on the Queue.
     * @return list of all enqueued events
     */
    public List<SystemEvent> getEvents() {
        List<SystemEvent> returnCollection = new ArrayList<>();
        eventQ.drainTo(returnCollection);
        return returnCollection;
    }

    /**
     * Returns the current size of the Queue.
     * @return size of the queue
     */
    public synchronized int getEventCount() {
        return eventQ.size();
    }
}
