package view;

import model.highscore.DatabaseResult;
import model.highscore.HighScore;
import model.highscore.HighScoreServer;

import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.List;

/**
 * File: PopUpMenu
 * Created: 16-11-23
 * Description: Creates a popup menu to be displayed over a diffrent frame.
 *
 * @author Andreas
 * @version 2.13
 */
public class PopUpMenu {
    private JFrame frame;
    private JTextArea textArea;

    /**
     * Creates an instance of the klass with the given string title and the
     * second string as the message being displayed and the last two int
     * arguments to define the size of the pop up screen.
     *
     * @param title:String
     * @param message:String
     * @param width:int
     * @param height:int
     */
    public PopUpMenu(String title, String message, int width, int height) {
        frame = new JFrame(title);
        frame.setLayout(new BorderLayout());
        frame.setMinimumSize(new Dimension(width, height));
        frame.setMaximumSize(new Dimension(width, height));
        frame.setPreferredSize(new Dimension(width, height));
        textArea = new JTextArea();

        textArea.setText(message);
        textArea.setEditable(false);

        //Build panels
        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.add(textArea);

        //Add panels to the frame
        frame.add(centerPanel, BorderLayout.CENTER);
        frame.setLocationRelativeTo(null);
        frame.pack();
        frame.setVisible(true);
    }

    /**
     * Sets the message on the pop up menu.
     *
     * @param message:String
     */
    public void setMessage(String message) {

        textArea.setText(message);
    }

    /**
     * appends the message on the pop up menu.
     *
     * @param message:String
     */
    public void appendMessage(String message) {

        textArea.append(message);
    }


}
