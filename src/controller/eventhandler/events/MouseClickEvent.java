package controller.eventhandler.events;

import model.level.Position;

/**
 * Author: Linus Lagerhjelm
 * File: MouseClickEvent
 * Created: 2016-12-14
 * Description: Indicates that the user has clicked somewhere on the board.
 * Contains the position that the user has clicked
 */
public class MouseClickEvent implements LevelEvent {

    private Position position;

    public MouseClickEvent(int x, int y) {
        position = new Position(x, y);
    }

    public Position getPosition() {
        return position;
    }
}
