package controller.eventhandler.events;

/**
 * Author: Linus Lagerhjelm
 * File: GameEvent
 * Created: 2016-12-06
 * Description:
 */
public class GameEvent extends SystemEvent {
    private boolean pause = false;
    private int currentLevel = 0;

    public GameEvent() {}

    public GameEvent(boolean pause) {
        this.pause = pause;
    }

    public GameEvent(int level) {
        currentLevel = level;
    }

    public GameEvent(boolean pause, int level) {
        this.pause = pause;
        currentLevel = level;
    }

    public int getCurrentLevel() {
        return currentLevel;
    }

    public void setPause(boolean value) {
        pause = value;
    }

    public boolean pauseGame() {
        return pause;
    }
}
