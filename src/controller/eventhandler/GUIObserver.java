package controller.eventhandler;

import controller.eventhandler.events.GameEvent;
import controller.eventhandler.events.QuitEvent;
import controller.eventhandler.events.RestartEvent;
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

            } else if (event.getActionCommand().equals("Pause")) {
                publisher.registerEvent(new GameEvent(true));

            } else if (event.getActionCommand().equals("Resume")) {
                publisher.registerEvent(new GameEvent(false));

            } else if (event.getActionCommand().equals("Restart")) {
                publisher.registerEvent(new RestartEvent());

            } else if (event.getActionCommand().equals("Quit")) {
                publisher.registerEvent(new QuitEvent());
            }

        } catch (UnableToRegisterEventException e) {
            e.printStackTrace();
        }
    }
}