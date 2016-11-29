package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * Created by c15aen on 2016-11-23. not default layout
 */
public class gameScreenPanel {

    private JPanel gameScreen;

    public gameScreenPanel() {
        gameScreen = new JPanel();
        gameScreen.setBackground(Color.BLACK);
    }

    public JPanel getGameScreen() {
        return gameScreen;
    }
}
