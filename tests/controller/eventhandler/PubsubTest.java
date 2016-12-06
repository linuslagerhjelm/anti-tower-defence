package controller.eventhandler;

import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Author: Linus Lagerhjelm
 * File: PubsubTest
 * Created: 2016-12-06
 * Description: Test cases for the PubsubTest
 */
public class PubsubTest {
    private final Pubsub PUBLISHER = new Pubsub();
    private final IsEvent EVENT = new IsEvent();
    private final IsEventSubscriber SUBSCRIBER = new IsEventSubscriber();

    @Test
    public void shouldRegistrerSubscriber() throws Exception {
        PUBLISHER.registerSubscriber(SUBSCRIBER);
        assertEquals(1, PUBLISHER.getSubscriberCount());
    }

    @Test
    public void shouldUnregisterSubscriber() throws Exception {
        PUBLISHER.unregisterSubscriber(SUBSCRIBER);
        assertEquals(0, PUBLISHER.getSubscriberCount());
    }

    @Test
    public void shouldRegisterEvent() throws Exception {
        PUBLISHER.registerEvent(EVENT);
        assertEquals(1, PUBLISHER.getEventCount());
    }

    @Test
    public void shouldGetEvents() throws Exception {
        PUBLISHER.registerEvent(EVENT);
        List<SystemEvent> events = PUBLISHER.getEvents();
        assertEquals(1, events.size());
    }

    @Test
    public void shouldNotKeepOldEvents() throws Exception {
        PUBLISHER.registerEvent(EVENT);
        PUBLISHER.getEvents();
        assertEquals(0, PUBLISHER.getEventCount());
    }

    private static class IsEventSubscriber implements EventSubscriber {

    }

    private static class IsEvent extends SystemEvent {

    }
}