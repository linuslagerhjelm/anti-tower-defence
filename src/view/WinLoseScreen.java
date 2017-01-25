package view;

import view.listeners.GameMenuListener;

import javax.swing.*;
import java.awt.*;

/**
 * File: WinLoseScreen
 * Created: 2016-12-6
 * A screen that display the win or lose screen for the user.
 *
 * @author Arvid
 */
public class WinLoseScreen {

    private static final Color BACKGROUND_COLOR = new Color(21, 160, 108);
    private static final Color BORDER_COLOR = new Color(225, 206, 159);

    private JPanel winLoseScreen;
    private JButton restartLevel;
    private JButton restartGame;
    private JButton quit;
    private JButton nextLevel;
    private ImageIcon winIcon;
    private ImageIcon loseIcon;

    /**
     * Constructor for the win lose screen displaying win/lose depending on
     * the given string argument.
     *
     * @param winLose:String
     * @param gml:GameMenuListener
     */
    public WinLoseScreen(String winLose, GameMenuListener gml, boolean lastLevel) {

        winLoseScreen = new JPanel(new BorderLayout());
        restartGame = new JButton("New Game");
        restartLevel = new JButton("Restart Level");
        quit = new JButton("Quit");
        if (!lastLevel) {
            nextLevel = new JButton("Next Level");
            nextLevel.addActionListener(gml);
        }
        restartGame.addActionListener(gml);
        restartLevel.addActionListener(gml);
        quit.addActionListener(gml);

        winIcon = new ImageIcon(this.getClass().getResource("/images/text/levelcomplete.png"));
        loseIcon = new ImageIcon(this.getClass().getResource("/images/text/gameover.png"));

        Dimension d = new Dimension(325, 325);

        JPanel buttonPanel = new JPanel();
        JPanel textPanel = new JPanel();
        JPanel emptyPanel1 = new JPanel();
        JPanel emptyPanel2 = new JPanel();
        JPanel emptyPanel3 = new JPanel();

        if (nextLevel != null) {
            buttonPanel.add(nextLevel);
        }

        buttonPanel.add(restartLevel);
        buttonPanel.add(restartGame);
        buttonPanel.add(quit);

        JLabel winLabel = new JLabel(winIcon);
        JLabel loseLabel = new JLabel(loseIcon);

        textPanel.setPreferredSize(d);
        emptyPanel1.setPreferredSize(d);
        emptyPanel3.setPreferredSize(d);
        winLoseScreen.add(textPanel, BorderLayout.NORTH);
        winLoseScreen.add(emptyPanel1, BorderLayout.EAST);
        winLoseScreen.add(emptyPanel2, BorderLayout.SOUTH);
        winLoseScreen.add(emptyPanel3, BorderLayout.WEST);
        winLoseScreen.add(buttonPanel, BorderLayout.CENTER);
        winLoseScreen.setVisible(true);


        winLoseScreen.setBorder(BorderFactory.createLineBorder(BORDER_COLOR, 8, false));

        if (winLose.equals("win")) {
            textPanel.add(winLabel);

        } else {
            buttonPanel.remove(nextLevel);
            textPanel.add(loseLabel);

        }

        textPanel.setBackground(BACKGROUND_COLOR);
        emptyPanel1.setBackground(BACKGROUND_COLOR);
        emptyPanel2.setBackground(BACKGROUND_COLOR);
        emptyPanel3.setBackground(BACKGROUND_COLOR);
        buttonPanel.setBackground(BACKGROUND_COLOR);
        winLoseScreen.setBackground(BACKGROUND_COLOR);
    }

    /**
     * Returns the winLoseScreen as a JPanel to be used as a JComponent
     *
     * @return winLoseScreen:JPanel
     */
    public JPanel getPanel() {

        return winLoseScreen;
    }


}
