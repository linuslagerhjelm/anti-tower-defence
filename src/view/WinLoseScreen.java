package view;

import javax.swing.*;
import java.awt.*;

/**
 * Created by c15aen on 2016-12-14.
 */
public class WinLoseScreen {

        private JPanel winLoseScreen;
        private JButton restartLevel;
        private JButton restartGame;
        private JButton quit;
        private JButton nextLevel;

        public WinLoseScreen(String winLose) {

                winLoseScreen = new JPanel(new BorderLayout());
                restartGame = new JButton("Restart Game");
                restartLevel = new JButton("Restart Level");
                quit = new JButton("Quit");
                nextLevel = new JButton("Next Level");

                Dimension d = new Dimension(325,325);

                JPanel buttonPanel = new JPanel();
                JPanel textPanel = new JPanel();
                JPanel emptyPanel2 = new JPanel();
                JPanel emptyPanel3 = new JPanel();
                JPanel emptyPanel4 = new JPanel();
                buttonPanel.add(nextLevel);
                buttonPanel.add(restartLevel);
                buttonPanel.add(restartGame);

                buttonPanel.add(quit);

                textPanel.setPreferredSize(d);
                emptyPanel2.setPreferredSize(d);
                emptyPanel4.setPreferredSize(d);
                winLoseScreen.add(textPanel, BorderLayout.NORTH);
                winLoseScreen.add(emptyPanel2, BorderLayout.EAST);
                winLoseScreen.add(emptyPanel3, BorderLayout.SOUTH);
                winLoseScreen.add(emptyPanel4, BorderLayout.WEST);
                winLoseScreen.add(buttonPanel, BorderLayout.CENTER);
                winLoseScreen.setVisible(true);


                winLoseScreen.setBorder(BorderFactory.createLineBorder(Color.CYAN,8,false));

                if(winLose.equals("win")) {
                        textPanel.add(new JTextArea("test"));

                }
                else {
                       buttonPanel.remove(nextLevel);

                }


                textPanel.setBackground(Color.ORANGE);
                emptyPanel2.setBackground(Color.GREEN);
                emptyPanel3.setBackground(Color.YELLOW);
                emptyPanel4.setBackground(Color.DARK_GRAY);
                buttonPanel.setBackground(Color.PINK);
                winLoseScreen.setBackground(Color.BLUE);





        }

        public JPanel getPanel() {

                return winLoseScreen;
        }


}
