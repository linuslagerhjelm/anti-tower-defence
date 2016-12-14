/*
 * File: Renderer.java
 * Author: Fredrik Johansson
 * Date: 2016-12-06
 */
package controller.game;

import model.entities.Entity;
import model.level.Line;
import view.GameScreenPanel;
import view.InfoPanel;

import java.util.Collection;

public class Renderer {

    private final GameScreenPanel board;
    private final InfoPanel info;


    public Renderer(GameScreenPanel board, InfoPanel info) {
        this.board = board;
        this.info = info;
    }

    public void clear() {
        board.refresh();
    }

    public void render(Collection<? extends Entity> entities) {
        for (Entity entity : entities) {
            board.drawTroop(entity.getFilePath(), entity.getPosition().getX(),
                    entity.getPosition().getY());
        }
    }

    public void renderLasers(Collection<Line> shots) {
        for (Line shot : shots) {
            board.drawLaser(shot.getPos1().getX(), shot.getPos1().getY(),
                            shot.getPos2().getX(), shot.getPos2().getY());
        }
    }

    public void renderMoney(String money) {
        info.setMoney(money);
    }
}
