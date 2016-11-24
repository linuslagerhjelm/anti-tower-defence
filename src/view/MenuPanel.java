package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

/**
 * Created by c15aen on 2016-11-23.
 */
public class MenuPanel {

        private JMenuBar menuBar = new JMenuBar();
        private JPanel menuPanel;
        private JMenuItem pauseStateButton;
        private JMenuItem quitButton;
        private JMenuItem gameStateButton;
        private JMenuItem restartLevelButton;


        public MenuPanel(int width, int height, String menuName) {

                //JMenu menu = new JMenu(menuName);

                menuPanel = new JPanel();
                menuPanel.setSize(width, height);
                menuPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
                //pauseStateButton = new JMenuItem("Pause");
                //quitButton = new JMenuItem("Quit");
                //gameStateButton = new JMenuItem("New Game");
                //restartLevelButton = new JMenuItem("Restart Level");
                restartLevelButton.setEnabled(false);

                //menu.add(pauseStateButton);
                //menu.add(quitButton);
                //menu.add(gameStateButton);
                //menu.add(restartLevelButton);
                //menuBar.add(menu);

                String[] buttonNames = new String[2];
                buttonNames[0] = "Pause";
                buttonNames[1] = "Quit";

                String[] buttonNames2 = new String[2];
                buttonNames2[0] = "About";
                buttonNames2[1] = "Help";

                createMenu(buttonNames, "MainMenu");
                createMenu(buttonNames2, "Info");

                menuPanel.add(menuBar);


        }

        public void setMenuListener(int index){

        }



        public void createMenu(String[] inString, String menuName, ActionListener menuListener){
                Menu newMenu = new Menu(inString, menuName);
                newMenu.setButtonAction(menuListener);
                menuBar.add(newMenu.getMenu());
        }

        public void setMenuVisibility(boolean visible) {
                menuBar.setVisible(visible);
        }

        public void changeStatePauseButton() {

                if (pauseStateButton.getText().equals("Pause")) {
                        pauseStateButton.setText("Resume");
                } else {
                        pauseStateButton.setText("Pause");
                }

        }

        public void changeGameStateButton() {

                if (gameStateButton.getText().equals("New Game")) {
                        gameStateButton.setText("Restart Game");
                } else {
                        gameStateButton.setText("New Game");
                }

        }

        public void setRestartLevelButton(boolean enabled) {
                restartLevelButton.setEnabled(enabled);
        }

        public JPanel returnPanel() {
                return menuPanel;
        }

}