package view;

import javax.swing.*;
import java.awt.*;

/**
 * Created by c15aen on 2016-11-23. INTE DEFAULT, SLUTA KLAGA!
 */
public class InfoPanel {

        private JPanel infoPanel;

        private JTextField highScoreField = new JTextField(10);
        private JTextField currentScoreField = new JTextField("0",10);
        private JTextField moneyField = new JTextField(10);
        private JTextField passedField = new JTextField(10);

        private JLabel highScoreLabel = new JLabel("Highscore");
        private JLabel currentScoreLabel = new JLabel("Score");
        private JLabel moneyLabel = new JLabel("Money");
        private JLabel passedLabel = new JLabel("Passed");

        private int requiredPasses = 10;
        private int passed = 0;
        private int currentScore = 0;
        private int money = 0;
        private int highScore = 0;


        public InfoPanel() {

                infoPanel = new JPanel();
                infoPanel.setBorder(BorderFactory.createTitledBorder("Game Info"));

                infoPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
                infoPanel.add(highScoreLabel);
                infoPanel.add(highScoreField);
                infoPanel.add(currentScoreLabel);
                infoPanel.add(currentScoreField);
                infoPanel.add(moneyLabel);
                infoPanel.add(moneyField);
                infoPanel.add(passedLabel);
                infoPanel.add(passedField);

                highScoreField.setEditable(false);
                currentScoreField.setEditable(false);
                moneyField.setEditable(false);
                passedField.setEditable(false);

        }
        public JPanel getPanel() {

                return infoPanel;
        }

        public void setScore(int newCurrentScore) {

                currentScore = newCurrentScore;
        }

        public void addScore(int scoreToAdd) {

                currentScore += scoreToAdd;
        }

        public void setHighScore(int newHighScore) {

                highScore = newHighScore;
        }

        public void setRequiredPasses(int newRequiredPasses) {

                requiredPasses = newRequiredPasses;
        }

        public void incrementPassed() {

                passed++;
        }

        public void setPassed(int newPassed) {

                passed = newPassed;
        }

        public void setMoney(int newMoney) {

                money = newMoney;
        }

        public void addMoney(int moneyToAdd) {

                money+=moneyToAdd;
        }

        public void displayInfo() {
                highScoreField.setText(""+highScore);
                currentScoreField.setText(""+currentScore);
                moneyField.setText(""+money);
                passedField.setText(passed+"/"+requiredPasses);
        }
}
