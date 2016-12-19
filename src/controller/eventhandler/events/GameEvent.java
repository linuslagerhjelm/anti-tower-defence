package controller.eventhandler.events;

/**
 * Author: Linus Lagerhjelm
 * File: GameEvent
 * Created: 2016-12-06
 * Description: Extensions of this class represents a subset of SystemEvents
 * that indicates some action related to the running status of the game.
 */
public class GameEvent implements SystemEvent {
    private boolean pause = false;

    public GameEvent() {}

    public GameEvent(boolean pause) {
        this.pause = pause;
    }

    public void setPause(boolean value) {
        pause = value;
    }

    public boolean pauseGame() {
        return pause;
    }
}
