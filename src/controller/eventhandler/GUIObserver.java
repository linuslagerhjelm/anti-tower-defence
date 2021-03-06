package controller.eventhandler;

import controller.eventhandler.events.*;
import view.listeners.InfoMenuListener;
import exceptions.UnableToRegisterEventException;
import model.highscore.HighScoreServer;
import view.PopUpMenu;

import java.awt.event.ActionEvent;

/**
 * Author: Linus Lagerhjelm
 * File: GUIObserver
 * Created: 2016-12-07
 * Description: Object that implements the Observer interface in order for
 * it to observe the GUI. Handles the mapping between GUI events and system
 * events.
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

            } else if (event.getActionCommand().equals("Next Level")) {
                publisher.registerEvent(new NextLevelEvent());

            } else if (event.getActionCommand().equals("Restart")) {
                publisher.registerEvent(new RestartEvent());

            } else if (event.getActionCommand().equals("Quit")) {
                publisher.registerEvent(new QuitEvent());

            } else if (event.getActionCommand().equals("Highscores")) {
                if (HighScoreServer.getInstance().getInitialized()) {
                    HighScoreServer.getInstance().getHighScores(result -> {
                        PopUpMenu menu = ((InfoMenuListener)subject).getHighScoreMenu();
                        result.forEach(highScore -> menu.appendMessage(highScore.getHighScoreString()+"\n"));
                    });
                }

            } else if (event.getActionCommand().equalsIgnoreCase("next")) {
                publisher.registerEvent(new NextTroupeEvent());

            } else if (event.getActionCommand().equalsIgnoreCase("prev")) {
                publisher.registerEvent(new PrevTroupeEvent());

            } else if (event.getActionCommand().startsWith("MOUSE_CLICKED")) {
                handleMouseEvent(event);
            }

        } catch (UnableToRegisterEventException e) {
            // ignore
        }
    }

    /**
     * Parses an action event with param-string as action event into a new
     * mouse-event that the system can understand and registers it at the
     * Pubsub.
     * @param event ActionEvent where the action command is a mouse event
     *              param string.
     * @throws UnableToRegisterEventException
     */
    private void handleMouseEvent(ActionEvent event) throws UnableToRegisterEventException {
        String[] parts = event.getActionCommand().replaceAll("[()]", "").split(",");
        publisher.registerEvent(new MouseClickEvent(Integer.parseInt(parts[1]),Integer.parseInt(parts[2])));
    }
}
