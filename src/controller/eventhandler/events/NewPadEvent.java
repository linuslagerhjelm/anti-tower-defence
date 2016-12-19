package controller.eventhandler.events;

/**
 * Author: Linus Lagerhjelm
 * File: NewPadEvent
 * Created: 2016-12-12
 * Description: Represents that a new pad has been placed on the board.
 * Contains information about the pad's placement, size and type.
 */
public class NewPadEvent implements LevelEvent {
    private String type;
    private int x;
    private int y;
    private int height;
    private int width;

    public NewPadEvent(String type, int x, int y, int height, int width) {
        this.x = x;
        this.y = y;
        this.height = height;
        this.width = width;
    }

    public String getType() {
        return type;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}
