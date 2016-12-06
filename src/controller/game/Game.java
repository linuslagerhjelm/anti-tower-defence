/*
 * File: Game.java
 * Author: Fredrik Johansson
 * Date: 2016-12-01
 */
package controller.game;

import controller.eventhandler.GameEvent;
import controller.eventhandler.SystemEvent;
import controller.eventhandler.Pubsub;
import model.level.Level;
import model.level.LevelReader;
import model.level.ParseResult;
import view.MainWindow;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class Game {
    private LevelReader levelReader;
    private List<Level> levels;
    private Pubsub publisher;
    private MainWindow mainWindow;
    private int currentLevel = 0;
    private boolean isPaused = false;

    public Game() {
        setup("level.xml");
    }

    public Game(String levelFile) {
        setup(levelFile);
    }

    /**
     * Calls all the setup methods in order to get going
     * @param levelFile name of the level file
     */
    private void setup(String levelFile) {
        setupLevels(levelFile);

        publisher = new Pubsub();
        mainWindow = MainWindow.getInstance();
        mainWindow.setVisible();
    }

    /**
     * Initiates parsing of level files on own thread. Starts the game loop
     * when levels has finished parsing.
     * @param levelFile filename to level path
     */
    private void setupLevels(String levelFile) {
        levelReader = new LevelReader(levelFile,
                "level_schema.xsd", new ParseResult() {

            @Override
            public void onSuccess(List<Level> read) {
                levels = read;
                run();
            }

            @Override
            public void onError(Exception e) {

            }
        });
    }


    /**
     * Runs the game loop and handles the system events
     */
    public void run() {
        long time = new Date().getTime();
        while (true) {
            long dt = new Date().getTime() - time;

            if (!isPaused) {
                levels.get(currentLevel).receiveEvents(handleEventQueue());
                levels.get(currentLevel).update(dt);

            } else {
                handleEventQueue();
            }


            time = new Date().getTime();
        }
    }

    /**
     * Retrieves all the events from the event Queue, handles the game events
     * and returns the level events.
     * @return List of level events
     */
    private List<SystemEvent> handleEventQueue() {
        List<SystemEvent> allEvents = publisher.getEvents();

        List<SystemEvent> gameEvents = allEvents.stream()
                .filter(e -> e instanceof GameEvent)
                .collect(Collectors.toList());

        List<SystemEvent> levelEvents = allEvents.stream()
                .filter(e -> e instanceof GameEvent)
                .collect(Collectors.toList());

        handleGameEvents(gameEvents);

        return levelEvents;
    }

    /**
     * Performs the actions proposed by the events
     * @param gameEvents list of game events
     */
    private void handleGameEvents(List<SystemEvent> gameEvents) {
        gameEvents.forEach(e -> {
            isPaused = ((GameEvent)e).pauseGame();
            currentLevel = ((GameEvent)e).getCurrentLevel();
        });
    }

}
