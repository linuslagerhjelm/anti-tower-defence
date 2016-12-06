package controller.eventhandler;

import exceptions.UnableToRegisterEventException;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Author: Linus Lagerhjelm
 * File: Pubsub
 * Created: 2016-12-06
 * Description:
 */
public class Pubsub {
    private List<EventSubscriber> subscribers = new ArrayList<>();
    private LinkedBlockingQueue<SystemEvent> eventQ = new LinkedBlockingQueue<>();

    public synchronized void registerSubscriber(EventSubscriber subscriber) {
        subscribers.add(subscriber);
    }

    public synchronized void unregisterSubscriber(EventSubscriber subscriber) {
        subscribers.remove(subscriber);
    }

    public void registerEvent(SystemEvent event)
            throws UnableToRegisterEventException {

        try {
            eventQ.put(event);

        } catch (InterruptedException e) {
            throw new UnableToRegisterEventException("Interrupted operation");
        }
    }

    public List<SystemEvent> getEvents() {
        List<SystemEvent> returnCollection = new ArrayList<>();
        eventQ.drainTo(returnCollection);
        return returnCollection;
    }

    public int getSubscriberCount() {
        return subscribers.size();
    }

    public int getEventCount() {
        return eventQ.size();
    }
}
