package controller.eventhandler.events;

/**
 * Author: Linus Lagerhjelm
 * File: GameEvent
 * Created: 2016-12-06
 * Description:
 */
public class GameEvent extends SystemEvent {
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
