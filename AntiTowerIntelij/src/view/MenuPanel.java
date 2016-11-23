package view;

import javax.swing.*;

/**
 * Created by c15aen on 2016-11-23.
 */
public class MenuPanel {

        private JMenu menu;
        private JMenuBar menuBar = new JMenuBar();
        private JPanel menuPanel;
        private JMenuItem pauseStateButton;
        private JMenuItem quitButton;
        private JMenuItem gameStateButton;
        private JMenuItem restartLevelButton;


        public MenuPanel(int width, int height, String menuName) {

                menu = new JMenu(menuName);
                menuPanel = new JPanel();
                pauseStateButton = new JMenuItem("Pause");
                quitButton = new JMenuItem("Quit");
                gameStateButton = new JMenuItem("New Game");
                restartLevelButton = new JMenuItem("Restart Level");
                restartLevelButton.setEnabled(false);
                menuPanel.setSize(width, height);

                menu.add(pauseStateButton);
                menu.add(quitButton);
                menu.add(gameStateButton);
                menu.add(restartLevelButton);

                menuBar.add(menu);
                menuPanel.add(menuBar);


        }
        public void setMenuVisibility(boolean visible) {

                menuBar.setVisible(visible);
        }

        public void changeStatePauseButton() {

                if(pauseStateButton.getText().equals("Pause")) {
                        pauseStateButton.setText("Resume");
                }
                else {
                        pauseStateButton.setText("Pause");
                }

        }

        public void changeGameStateButton() {

                if(gameStateButton.getText().equals("New Game")) {
                        gameStateButton.setText("Restart Game");
                }
                else {
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
