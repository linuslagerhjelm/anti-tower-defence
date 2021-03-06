package view;

/**
 * Date: 2016-11-03
 *
 * @author Andreas
 * @author Arvid
 * @version 2
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
 * File: MainWindow
 * Created: 2016-12-6
 * Singleton mainframe for the antiTowerDefence game.
 *
 * @author Andreas
 */
public class MainWindow {

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
    private WinLoseScreen winLastLevel;
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
            mainWindowInstance = new MainWindow("Anti- Tower Defence 3000", 800, 600);
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

            gameScreenPanel = new GameScreenPanel(mouseListener);

            setupGameMenu();
            setupInfoMenu();
            winScreen = new WinLoseScreen("win", gameMenuListener, false);
            loseScreen = new WinLoseScreen("lose", gameMenuListener, false);
            winLastLevel = new WinLoseScreen("win", gameMenuListener, true);

            //Build panels
            upperPanel = menuPanel.returnPanel();
            rightPanel = troopMakerPanel.getJPanel();
            lowerPanel = infoPanel.getPanel();
            centerPanel = gameScreenPanel.getJPanel();

            guiListeners.addAll(troopMakerPanel.getActionListeners());

            //Add panels to the frame
            frame.add(upperPanel, BorderLayout.NORTH);
            frame.add(rightPanel, BorderLayout.EAST);
            frame.add(lowerPanel, BorderLayout.SOUTH);
            frame.add(centerPanel, BorderLayout.CENTER);

            frame.setResizable(false);
            //showWin();
            //showLose();
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

    /**
     * returns the mouse listener being used.
     * @return mouseListener
     */
    public MouseListener getMouseListener() {

        return mouseListener;
    }

    /**
     * Shows the win screen on the frame.
     */
    public void showWin() {
        SwingUtilities.invokeLater(() -> {
            frame.remove(rightPanel);
            frame.remove(lowerPanel);
            frame.remove(centerPanel);
            winLosePanel = winScreen.getPanel();
            frame.add(winLosePanel, BorderLayout.CENTER);
            frame.repaint();
            frame.revalidate();
        });
    }

    public void showWinLast() {
        SwingUtilities.invokeLater(() -> {
            frame.remove(rightPanel);
            frame.remove(lowerPanel);
            frame.remove(centerPanel);
            winLosePanel = winLastLevel.getPanel();
            frame.add(winLosePanel, BorderLayout.CENTER);
            frame.repaint();
            frame.revalidate();
        });
    }

    /**
     * Shows the lose screen on the frame.
     */
    public void showLose() {
        SwingUtilities.invokeLater(() -> {
            frame.remove(rightPanel);
            frame.remove(lowerPanel);
            frame.remove(centerPanel);
            winLosePanel = loseScreen.getPanel();
            frame.add(winLosePanel, BorderLayout.CENTER);
            frame.repaint();
            frame.revalidate();
        });
    }

    /**
     * Shows the game screen on the frame.
     */
    public void showGame() {
        SwingUtilities.invokeLater(() -> {
            if (winLosePanel != null) {
                frame.remove(winLosePanel);
            }
            frame.add(upperPanel, BorderLayout.NORTH);
            frame.add(rightPanel, BorderLayout.EAST);
            frame.add(lowerPanel, BorderLayout.SOUTH);
            frame.add(centerPanel, BorderLayout.CENTER);
            frame.repaint();
        });
    }


    /**
     * Get the gameScreen panel.
     *
     * @return GameScreenPanel:JPanel,
     */
    public GameScreenPanel getGameScreen() {
        try {
            SwingUtilities.invokeAndWait(() -> {
                // Just wait for swing thread to finish
            });
        } catch (InterruptedException e) {
            // ignore
        } catch (InvocationTargetException e) {
            // can't happen, no code runs
        }
        return gameScreenPanel;
    }

    /**
     * Gets the information panel
     * @return InfoPanel
     */
    public InfoPanel getInfoPanel() {
        try {
            SwingUtilities.invokeAndWait(() -> {
                // Just wait for swing thread to finish
            });
        } catch (InterruptedException e) {
            // ignore
        } catch (InvocationTargetException e) {
            // can't happen, no code runs
        }
        return infoPanel;
    }

    /**
     * Setups the GameMenu for the main frame.
     */
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

    /**
     * Setups the information menu for the main frame.
     */
    private void setupInfoMenu() {
        String[] infoButtonNames = {"About","Help", "Highscores"};
        InfoMenuListener infoMenuListener = new InfoMenuListener(infoButtonNames);
        guiListeners.add(infoMenuListener);
        menuPanel.createMenu(infoButtonNames, "Info", infoMenuListener);
    }

    /**
     * Creates a message in the event of a fatal error.
     * @param message:string error code.
     */
    public void fatalError(String message) {
        try {
            SwingUtilities.invokeAndWait(() -> {
                // add html to make word wraps
                JOptionPane.showMessageDialog(frame,
                        "<html><body><p style='width: 300px;'>"+message+"</p></body></html>",
                        "Error", JOptionPane.ERROR_MESSAGE);
            });
        } catch (InterruptedException | InvocationTargetException e) {
            // ignore
        }
    }

    /**
     * Sets the troupe information panel to the given parameters value.
     *
     * @param title:string
     * @param cost:int
     * @param speed:int
     * @param health:int
     * @param description:String
     * @param icon:Sring, to icon path.
     */
    public void setTroupeInfo(String title, int cost, int speed, int health, String description, String icon) {
        SwingUtilities.invokeLater(() -> {
            troopMakerPanel.setTroopImage(icon);
            troopMakerPanel.changeUnitInfo("Type: " + title + "\n" +
                    "Cost: " + cost + "\n" +
                    "Health: " + health + "\n" +
                    "Speed: " + speed + "\n" +
                    "Description: " + description + "\n");
        });
    }
}
