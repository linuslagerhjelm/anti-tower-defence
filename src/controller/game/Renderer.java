/*
 * File: Renderer.java
 * Author: Fredrik Johansson
 * Date: 2016-12-06
 */
package controller.game;

import javafx.print.Collation;
import model.entities.Entity;
import model.highscore.HighScoreServer;
import model.level.Line;
import view.GameScreenPanel;
import view.InfoPanel;
import view.MainWindow;
import view.PopUpMenu;
import view.listeners.InfoMenuListener;

import javax.swing.*;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Responsible for painting the game with all it's entities
 */
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

    /**
     * Paints all the entities on the game board
     * @param entities the entities that is currently active
     */
    public void render(Collection<? extends Entity> entities) {
        for (Entity entity : entities) {
            board.drawEntity(entity.getFilePath(), entity.getPosition().getX(),
                    entity.getPosition().getY());
        }
    }

    /**
     * Paints the lasers of the towers
     * @param shots the shot to be painted
     */
    public void renderLasers(Collection<Line> shots) {
        for (Line shot : shots) {
            board.drawLaser(shot.getPos1().getX(), shot.getPos1().getY(),
                            shot.getPos2().getX(), shot.getPos2().getY());
        }
    }

    public void renderPassed(int passed, int outOf) {
        info.setPassed(passed);
        info.setRequiredPasses(outOf);
    }

    /**
     * Updates the money in the info panel
     * @param money current money
     */
    public void renderMoney(String money) {
        info.setMoney(money);
    }

    public void renderHighscore(int highscore) {
        info.setHighScore(highscore);
    }

    public void displayHighScore() {
        if (HighScoreServer.getInstance().getInitialized()) {
            HighScoreServer.getInstance().getHighScores(result -> {
                Collection<String> res = result.stream()
                        .map(highScore -> highScore.getHighScoreString()+"\n")
                        .collect(Collectors.toList());
                String buf = "Level        Time        Score      \n";
                for (String str:res) {
                    buf += str;
                }
                JDialog dialog = new JDialog();
                JTextArea label = new JTextArea(buf);
                label.setEditable(false);
                dialog.setLocationRelativeTo(null);
                dialog.setTitle("Scores");
                dialog.add(label);
                dialog.setModal(true);
                dialog.pack();
                dialog.setVisible(true);
            });
        }


    }

    public void renderScore(int score) {
        info.setScore(score);
    }

    /**
     * Paints the texture of the level
     * @param path filepath to the image texture
     */
    public void setLevelTexture(String path) {
        board.setLevelImage(path);
    }
}
