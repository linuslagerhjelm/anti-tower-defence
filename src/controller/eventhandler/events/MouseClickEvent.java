package controller.eventhandler.events;

import model.level.Position;

/**
 * Author: Linus Lagerhjelm
 * File: MouseClickEvent
 * Created: 2016-12-14
 * Description:
 */
public class MouseClickEvent extends LevelEvent {

    private Position position;

    public MouseClickEvent(int x, int y) {
        position = new Position(x, y);
    }

    public Position getPosition() {
        return position;
    }
}
