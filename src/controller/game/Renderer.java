/*
 * File: Renderer.java
 * Author: Fredrik Johansson
 * Date: 2016-12-06
 */
package controller.game;

import model.entities.*;
import view.GameScreenPanel;
import view.MainWindow;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Renderer {

    private final GameScreenPanel board;

    public Renderer(GameScreenPanel board) {
        this.board = board;
    }

    public void render(List<Entity> entities) {
        for (Entity entity : entities) {
            board.drawTroop(entity.getPosition());
        }
        board.refresh();
    }


    public static void main(String[] args) {
        MainWindow window = MainWindow.getInstance();
        window.setVisible();
        Renderer renderer = new Renderer(window.getGameScreen());

        List<Entity> entities = new ArrayList<>();
        Troupe troupe = new TeleportTroupe();


        Path p = new Path();
        Node n1 = new Node(1, 1, 1);
        Node n2 = new Node(2, 400, 400);
        Node n3 = new Node(3, 200, 50);
        Node n4 = new Node(3, 300, 50);
        n1.setStart();
        n3.setGoal();
        n4.setGoal();
        n1.addSuccessor(n2);
        n1.addSuccessor(n4);
        n2.addSuccessor(n3);

        HashMap<Integer, Node> hm = new HashMap<>();
        hm.put(n1.getId(), n1);
        hm.put(n2.getId(), n2);
        hm.put(n3.getId(), n3);
        hm.put(n4.getId(), n4);
        p.addNodes(hm);
        p.isValid();

        troupe.setStartNode(n1);
        entities.add(troupe);

        int time = 30;
        for (int i = 0; i < 5000; i++) {
            troupe.update((double) time/1000);
            renderer.render(entities);
            try {
                Thread.sleep(time);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
