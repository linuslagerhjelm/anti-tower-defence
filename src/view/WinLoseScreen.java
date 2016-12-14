package view;

import view.listeners.GameMenuListener;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.*;
import java.util.List;

/**
 * Created by c15aen on 2016-12-14.
 */
public class WinLoseScreen {

        private JPanel winLoseScreen;
        private JButton restartLevel;
        private JButton restartGame;
        private JButton quit;
        private JButton nextLevel;
        private ImageIcon winIcon;
        private ImageIcon loseIcon;

        public WinLoseScreen(String winLose, GameMenuListener gml)  {

                winLoseScreen = new JPanel(new BorderLayout());
                restartGame = new JButton("New Game");
                restartLevel = new JButton("Restart Level");
                quit = new JButton("Quit");
                nextLevel = new JButton("Next Level");
                restartGame.addActionListener(gml);
                restartLevel.addActionListener(gml);
                quit.addActionListener(gml);
                nextLevel.addActionListener(gml);
                try {
                        winIcon = new ImageIcon(this.getClass().getResource("/images/text/levelcomplete.png"));
                        loseIcon = new ImageIcon(this.getClass().getResource("/images/text/gameover.png"));
                } catch (Exception e) {
                        e.printStackTrace();
                }

                Dimension d = new Dimension(325,325);

                JPanel buttonPanel = new JPanel();
                JPanel textPanel = new JPanel();
                JPanel emptyPanel1 = new JPanel();
                JPanel emptyPanel2 = new JPanel();
                JPanel emptyPanel3 = new JPanel();

                buttonPanel.add(nextLevel);
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


                winLoseScreen.setBorder(BorderFactory.createLineBorder(Color.CYAN,8,false));

                if(winLose.equals("win")) {
                        textPanel.add(winLabel);

                }
                else {
                       buttonPanel.remove(nextLevel);
                        textPanel.add(loseLabel);

                }


                textPanel.setBackground(Color.BLUE);
                emptyPanel1.setBackground(Color.BLUE);
                emptyPanel2.setBackground(Color.BLUE);
                emptyPanel3.setBackground(Color.BLUE);
                buttonPanel.setBackground(Color.BLUE);
                winLoseScreen.setBackground(Color.BLUE);





        }

        public JPanel getPanel() {

                return winLoseScreen;
        }


}
