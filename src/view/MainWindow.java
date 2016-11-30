package view; /**
 * Created by c15aen on 2016-11-03. teset seteste
 */

import control.GameMenuListener;
import control.MenuListener;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * view.MainWindow creates a simple GUI consisting of three panels. Largely based on the class ThreePanels by Johan Eliasson.
 */
public class MainWindow {

        private JFrame frame;

        private MenuPanel menuPanel;
        private TroopMakerPanel troopMakerPanel;

        public MainWindow(String title, int width, int height) {
                frame = new JFrame(title);
                frame.setLayout(new BorderLayout());
                frame.setMinimumSize(new Dimension(width,height));
                frame.setMaximumSize(new Dimension(width,height));
                frame.setPreferredSize(new Dimension(width, height));
                frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

                troopMakerPanel = new TroopMakerPanel(12, 21);
                menuPanel = new MenuPanel(50,50,"Menu bar");

                setupGameMenu();
                setupInfoMenu();

                //Build panels
                JPanel upperPanel = menuPanel.returnPanel();
                JPanel rightPanel = troopMakerPanel.getJPanel();

                //Add panels to the frame
                frame.add(upperPanel, BorderLayout.NORTH);
                frame.add(rightPanel, BorderLayout.EAST);

                frame.pack();

        }

        private void setupGameMenu(){
                String[] menuButtonNames = {"New Game","Restart Level","Pause","Quit"};
                GameMenuListener gameMenuListener = new GameMenuListener(menuButtonNames);
                menuPanel.createMenu(menuButtonNames, "Main Menu", gameMenuListener);
        }

        private void setupInfoMenu(){
                String[] infoButtonNames = {"About","Help"};
                MenuListener infoMenuListener = new MenuListener(infoButtonNames);
                menuPanel.createMenu(infoButtonNames, "Info", infoMenuListener);
        }

        /**
         * Sets the GUI to be visible.
         */
        public void setVisible() {
                frame.setVisible(true);
        }
}
