/*
 * File: Game.java
 * Author: Fredrik Johansson
 * Date: 2016-12-01
 */
package controller.game;

import controller.eventhandler.GUIObserver;
import controller.eventhandler.Observable;
import controller.eventhandler.Observer;
import controller.eventhandler.Pubsub;
import controller.eventhandler.events.*;
import exceptions.InvalidConnectionDataException;
import model.entities.troupe.TroupeFactory;
import model.entities.troupe.TroupeStats;
import model.highscore.DatabaseConfig;
import model.highscore.HighScore;
import model.highscore.HighScoreServer;
import model.highscore.Score;
import model.level.Level;
import model.level.LevelReader;
import model.level.ParseResult;
import view.MainWindow;

import javax.swing.*;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class Game implements Level.WinListener, ParseResult {

    private Pubsub publisher;
    private Observer observer;
    private MainWindow mainWindow;
    private Renderer renderer;
    private HighScoreServer highScores = HighScoreServer.getInstance();

    private LevelReader levelReader;
    private List<Level> levels;
    private boolean running = true;
    private int currentLevel = 0;
    private boolean isPaused = false;
    private List<TroupeStats> stats = TroupeFactory.getTroupeStats();
    private int troupeIndex = 0;

    public Game() {
        setup("level1.xml");
    }

    public Game(String levelFile) {
        setup(levelFile);
    }

    /**
     * Calls all the setup methods in order to get going
     * @param levelFile name of the level file
     */
    private void setup(String levelFile) {
        publisher = new Pubsub();
        observer = new GUIObserver(publisher);

        try {
            String path = getClass().getResource(".db_config").getFile();
            highScores.initialize(new DatabaseConfig(path));
        } catch (InvalidConnectionDataException | NullPointerException e) {
            /* Continue running without database */
        }

        mainWindow = MainWindow.getInstance();

        renderer = new Renderer(mainWindow.getGameScreen(),
                                mainWindow.getInfoPanel()); // Blocking

        mainWindow.getGuiListeners().forEach(listener -> {
            if (listener instanceof Observable) {
                ((Observable) listener).registerObserver(observer);
            }
        });

        ((Observable)mainWindow.getMouseListener()).registerObserver(observer);

        setupLevels(levelFile);
    }

    /**
     * Initiates parsing of level files on own thread. Starts the game loop
     * when levels has finished parsing.
     * @param levelFile filename to level path
     */
    private void setupLevels(String levelFile) {
        levelReader = new LevelReader(levelFile, "level_schema.xsd", this);
        levelReader.run();
    }

    @Override // from ParseResult
    public void onSuccess(List<Level> read) {
        levels = read;
        for (Level level : levels) {
            level.setWinListener(this);
        }
        renderer.setLevelTexture(levels.get(currentLevel).getTexture());
        run();
    }

    @Override // from ParseResult
    public void onError(Exception e) {
        if (e.getMessage() != null) {
            mainWindow.fatalError(e.getMessage()); // blocking 'til user accepts
        } else {
            mainWindow.fatalError(e.toString()); // blocking
        }
    }


    /**
     * Runs the game loop and handles the system events
     */
    public void run() {

        long time = new Date().getTime();
        levels.get(currentLevel).start();
        while (running && !levels.get(currentLevel).hasLost()) {
            double dt = (new Date().getTime() - time)/1000.0; // sec
            time = new Date().getTime();

            if (!isPaused) {
                renderer.clear();
                levels.get(currentLevel).receiveEvents(handleEventQueue());
                levels.get(currentLevel).update(dt);
                renderer.render(levels.get(currentLevel).getTroupes());
                renderer.render(levels.get(currentLevel).getTowers());
                renderer.renderLasers(levels.get(currentLevel).getShots());
                levels.get(currentLevel).getShots().clear();
                renderer.renderMoney(levels.get(currentLevel).getMoney());

                try {
                    Thread.sleep(16);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } else {
                handleEventQueue();
            }
        }
    }


    @Override // from Level.WinListener
    public void onWin(Score score) {
        highScores.addHighScore(new HighScore(score, new Date(), currentLevel));
        nextLevel();
    }

    /**
     * Start next level
     */
    private void nextLevel() {
        currentLevel = Math.min(levels.size()-1, currentLevel+1);
        levels.get(currentLevel).setWinListener(this);
        renderer.setLevelTexture(levels.get(currentLevel).getTexture());
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
                .filter(e -> e instanceof LevelEvent)
                .collect(Collectors.toList());

        levelEvents.forEach(event -> {
            if (event instanceof SpawnEvent) {
                ((SpawnEvent)event).setType(stats.get(troupeIndex).getTitle());
            }
        });

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
            if (e instanceof QuitEvent) {
                running = false;
            } else if (e instanceof RestartEvent) {
                levels.set(currentLevel, levels.get(currentLevel).reset());
            } else if (e instanceof NextTroupeEvent) {
                troupeIndex = (++troupeIndex) % stats.size();
                updateTroupeInfo();
            } else if (e instanceof PrevTroupeEvent) {
                troupeIndex = (--troupeIndex < 0) ? stats.size() - 1 : troupeIndex;
                updateTroupeInfo();
            }
        });
    }

    private void updateTroupeInfo() {
        TroupeStats info = stats.get(troupeIndex);
        SwingUtilities.invokeLater(() -> {
            mainWindow.setTroupeInfo(info.getTitle(), info.getHealth(),
                info.getSpeed(), info.getDescription(), info.getImgPath());
        });
    }
}
