package controller.eventhandler;

import controller.eventhandler.events.SpawnEvent;
import exceptions.UnableToRegisterEventException;

import java.awt.event.ActionEvent;

/**
 * Author: Linus Lagerhjelm
 * File: GUIObserver
 * Created: 2016-12-07
 * Description:
 */
public class GUIObserver implements Observer {
    private Pubsub publisher;

    public GUIObserver(Pubsub publisher) {
        this.publisher = publisher;
    }

    /**
     * {@inheritDoc}
     * @param subject the observable that has changed
     * @param action the change that were made
     */
    @Override
    public void update(Observable subject, Object action) {
        ActionEvent event = (ActionEvent)action;

        try {
            if (event.getActionCommand().equals("spawn")) {
                publisher.registerEvent(new SpawnEvent());
            }

        } catch (UnableToRegisterEventException e) {
            e.printStackTrace();
        }
    }
}
