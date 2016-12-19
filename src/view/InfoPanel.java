package view;

import javax.swing.*;
import java.awt.*;

/**
 * File: InfoPanel
 * Created: 16-11-23
 * Description: Information panel to display money,
 * highscore and etc.
 *
 * @author Andreas
 * @author Arvid
 * @version 2
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
    private String money = "0$";
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

    /**
     * GetPanel returns the info menu's jPanel to be placed in as a component.
     * @return infoPanel:JPanel
     */
    public JPanel getPanel() {
        return infoPanel;
    }

    /**
     * Sets the current score to the given integer value
     * @param newCurrentScore:int, score to be set.
     */
    public void setScore(int newCurrentScore) {
        currentScore = newCurrentScore;
    }

    /**
     * Add score adds to the current score by the given value
     * of the argument integer.
     * @param scoreToAdd:int, value of score to add.
     */
    public void addScore(int scoreToAdd) {
        currentScore += scoreToAdd;
    }

    /**
     * Sets the value of the highscore field, with the given
     * argument integer value.
     * @param newHighScore:int, new highscore to be displayed.
     */
    public void setHighScore(int newHighScore) {
        highScore = newHighScore;
    }

    /**
     * Sets the display text of required passes to be made to win the level.
     * @param newRequiredPasses:int, number value of number of passes to be made.
     */
    public void setRequiredPasses(int newRequiredPasses) {
        requiredPasses = newRequiredPasses;
    }

    /**
     * Increase the display of number of passes that has been made
     */
    public void incrementPassed() {
        passed++;
    }

    /**
     * Sets the display number of number of passes that has been made.
     * @param newPassed:int, nr of passes made to be displayed.
     */
    public void setPassed(int newPassed) {
        passed = newPassed;
    }

    /**
     * sets the display number of the amount of money the user has.
     * @param newMoney:int, amount of money to be displayed.
     */
    public void setMoney(String newMoney) {
        SwingUtilities.invokeLater(() -> {
            money = newMoney;
            displayInfo();
        });
    }

    /**
     * Display info displays the information on the information panel.
     */
    private void displayInfo() {
        highScoreField.setText(""+highScore);
        currentScoreField.setText(""+currentScore);
        moneyField.setText(money);
        passedField.setText(passed+"/"+requiredPasses);
    }
}
