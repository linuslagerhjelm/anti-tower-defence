/*
 * File: Renderer.java
 * Author: Fredrik Johansson
 * Date: 2016-12-06
 */
package controller.game;

import model.entities.Entity;
import view.GameScreenPanel;

import java.util.List;

public class Renderer {

    private final GameScreenPanel board;

    public Renderer(GameScreenPanel board) {
        this.board = board;
    }

    public void render(List<? extends Entity> entities) {
        for (Entity entity : entities) {
            board.drawTroop(entity.getPosition());
        }
        board.refresh();
    }
}
