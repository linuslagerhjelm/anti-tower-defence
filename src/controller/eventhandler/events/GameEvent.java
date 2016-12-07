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
