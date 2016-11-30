package view;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

/**
 * Created by c15aen on 2016-11-23.
 */
public class InfoPanel {

        private JPanel infoPanel;
        private JTextField highScore = new JTextField(10);
        private JTextField currentScore = new JTextField(10);
        private JTextField money = new JTextField(10);
        private JTextField passed = new JTextField(10);
        private JLabel highScoreLabel = new JLabel("Highscore");
        private JLabel currentScoreLabel = new JLabel("Score");
        private JLabel moneyLabel = new JLabel("Money");
        private JLabel passedLabel = new JLabel("Passed");


        public InfoPanel() {

                infoPanel = new JPanel();
                infoPanel.setBorder(BorderFactory.createTitledBorder("Game Info"));

                infoPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
                infoPanel.add(highScoreLabel);
                infoPanel.add(highScore);
                infoPanel.add(currentScoreLabel);
                infoPanel.add(currentScore);
                infoPanel.add(moneyLabel);
                infoPanel.add(money);
                infoPanel.add(passedLabel);
                infoPanel.add(passed);

        }
        public JPanel getPanel() {
                return infoPanel;
        }
}
