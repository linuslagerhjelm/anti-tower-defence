package view;

/**
 * Created by c15aen on 2016-11-03. teset seteste
 */

import view.listeners.GameMenuListener;
import view.listeners.InfoMenuListener;
import view.listeners.MouseEventListener;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by c15aen on 2016-11-03.
 * Creates the frame and adds all needed components to it.
 */

public class MainWindow {

    private final String DEFAULT_IMAGE = "/images/levels/default_level.png";
    private JFrame frame;
    private GameMenuListener gameMenuListener;
    private JPanel upperPanel;
    private JPanel rightPanel;
    private JPanel lowerPanel;
    private JPanel centerPanel;
    private JPanel winLosePanel;
    private MenuPanel menuPanel;
    private TroopMakerPanel troopMakerPanel;
    private GameScreenPanel gameScreenPanel;
    private InfoPanel infoPanel;
    private WinLoseScreen winScreen;
    private WinLoseScreen loseScreen;
    private List<ActionListener> guiListeners = new ArrayList<>();
    private MouseListener mouseListener = new MouseEventListener();

    private static MainWindow mainWindowInstance = null;

    private MainWindow(){
        // Exists only to defeat instantiation.
    }
    /**
     * Creates an instance of the main window if no main windows has been made earlier.
     * @return The instance.
     */
    public static MainWindow getInstance() {
        if(mainWindowInstance == null) {
            mainWindowInstance = new MainWindow("Fiskare", 800, 600);
        }
        return mainWindowInstance;
    }

        /**
         * Creates the frame which holds all the games components.
         * @param title - The title of the frame.
         * @param width - The frames width.
         * @param height - The frames height.
         */
    private MainWindow(String title, int width, int height) {
        SwingUtilities.invokeLater(() -> {
            frame = new JFrame(title);
            frame.setLayout(new BorderLayout());
            //frame.setMinimumSize(new Dimension(width,height));
            frame.setMaximumSize(new Dimension(width,height));
            frame.setPreferredSize(new Dimension(width, height));
            frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

            troopMakerPanel = new TroopMakerPanel();
            menuPanel = new MenuPanel(50,50);
            infoPanel = new InfoPanel();

            gameScreenPanel = new GameScreenPanel(mouseListener, DEFAULT_IMAGE);

            setupGameMenu();
            setupInfoMenu();
            winScreen = new WinLoseScreen("win", gameMenuListener);
            loseScreen = new WinLoseScreen("lose", gameMenuListener);

            //Build panels
            upperPanel = menuPanel.returnPanel();
            rightPanel = troopMakerPanel.getJPanel();
            lowerPanel = infoPanel.getPanel();
            centerPanel = gameScreenPanel.getJPanel();
            //winLosePanel = winScreen.getPanel();

            guiListeners.addAll(troopMakerPanel.getActionListeners());

            //Add panels to the frame
            frame.add(upperPanel, BorderLayout.NORTH);
            frame.add(rightPanel, BorderLayout.EAST);
            frame.add(lowerPanel, BorderLayout.SOUTH);
            frame.add(centerPanel, BorderLayout.CENTER);

            frame.setResizable(false);
            showWin();
            showLose();
                showGame();

            //centerPanel.setVisible(false);

            frame.setLocationRelativeTo(null);
            frame.pack();

            centerPanel.setSize(new Dimension(513, 484));

            frame.setVisible(true);
        });
    }

        /**
         * Returns all listeners associated with the GUI.
         * @return - A list of all listeners.
         */
    public List<ActionListener> getGuiListeners() {

        return guiListeners;
    }

    public MouseListener getMouseListener() {

        return mouseListener;
    }

    public void showWin() {
        frame.remove(rightPanel);
        frame.remove(lowerPanel);
        frame.remove(centerPanel);
        winLosePanel = winScreen.getPanel();
        frame.add(winLosePanel, BorderLayout.CENTER);
    }

    public void showLose() {
        frame.remove(rightPanel);
        frame.remove(lowerPanel);
        frame.remove(centerPanel);
        winLosePanel = loseScreen.getPanel();
        frame.add(winLosePanel, BorderLayout.CENTER);
    }

        public void showGame() {
                frame.remove(winLosePanel);
                frame.add(upperPanel, BorderLayout.NORTH);
                frame.add(rightPanel, BorderLayout.EAST);
                frame.add(lowerPanel, BorderLayout.SOUTH);
                frame.add(centerPanel, BorderLayout.CENTER);

        }


        public GameScreenPanel getGameScreen() {
        try {
            SwingUtilities.invokeAndWait(() -> {
                // Just wait for swing thread to finish
            });
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return gameScreenPanel;
    }

    public InfoPanel getInfoPanel() {
        try {
            SwingUtilities.invokeAndWait(() -> {
                // Just wait for swing thread to finish
            });
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return infoPanel;
    }

    private void setupGameMenu() {
        String[] menuButtonNames = {"New Game","Pause","Quit"};
        gameMenuListener = new GameMenuListener(menuButtonNames);
        gameMenuListener.registerObserver((subject, action) -> {
            ActionEvent e = (ActionEvent)action;
            if (e.getActionCommand().equals(menuButtonNames[0])) {
                troopMakerPanel.enableButtons();
            }
        });
        guiListeners.add(gameMenuListener);
        menuPanel.createMenu(menuButtonNames, "Main Menu", gameMenuListener);
    }

    private void setupInfoMenu() {
        String[] infoButtonNames = {"About","Help", "Highscores"};
        InfoMenuListener infoMenuListener = new InfoMenuListener(infoButtonNames);
        guiListeners.add(infoMenuListener);
        menuPanel.createMenu(infoButtonNames, "Info", infoMenuListener);
    }

    public void fatalError(String message) {
        try {
            SwingUtilities.invokeAndWait(() -> {
                // add html to make word wraps
                JOptionPane.showMessageDialog(frame,
                        "<html><body><p style='width: 300px;'>"+message+"</p></body></html>", "Error",
                        JOptionPane.ERROR_MESSAGE);
            });
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    public void setTroupeInfo(String title, int health, int speed, String description, String icon) {
        troopMakerPanel.setTroopImage(icon);
        troopMakerPanel.changeUnitInfo("Type: " + title + "\n" +
                "Health: " + health + "\n" +
                "Speed: " + speed + "\n" +
                "Description: " + description + "\n");
    }
}
