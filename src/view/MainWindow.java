package view;
/**
 * Created by c15aen on 2016-11-03. teset seteste
 */

import view.listeners.GameMenuListener;
import view.listeners.InfoMenuListener;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.InvocationTargetException;

import java.util.ArrayList;
import java.util.List;

/**
 * view.MainWindow creates a simple GUI consisting of three panels.
 * Largely based on the class ThreePanels by Johan Eliasson.
 */
public class MainWindow {

    private final String DEFAULT_IMAGE = "/images/levels/defaultLevel.jpg";
    private JFrame frame;
    private MenuPanel menuPanel;
    private TroopMakerPanel troopMakerPanel;
    private GameScreenPanel gameScreenPanel;
    private InfoPanel infoPanel;
    private List<ActionListener> guiListeners = new ArrayList<>();

    private static MainWindow mainWindowInstance = null;

    private MainWindow(){
        // Exists only to defeat instantiation.
    }

    public static MainWindow getInstance() {
        if(mainWindowInstance == null) {
            mainWindowInstance = new MainWindow("Fiskare", 800, 600);
        }
        return mainWindowInstance;
    }

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
            gameScreenPanel = new GameScreenPanel(DEFAULT_IMAGE);

            setupGameMenu();
            setupInfoMenu();

            //Build panels
            JPanel upperPanel = menuPanel.returnPanel();
            JPanel rightPanel = troopMakerPanel.getJPanel();
            JPanel lowerPanel = infoPanel.getPanel();
            JPanel centerPanel = gameScreenPanel.getJPanel();

            guiListeners.addAll(troopMakerPanel.getActionListeners());

            //Add panels to the frame
            frame.add(upperPanel, BorderLayout.NORTH);
            frame.add(rightPanel, BorderLayout.EAST);
            frame.add(lowerPanel, BorderLayout.SOUTH);
            frame.add(centerPanel, BorderLayout.CENTER);
            frame.setResizable(false);

            frame.setLocationRelativeTo(null);
            frame.pack();

            centerPanel.setSize(new Dimension(1000,1000));

            frame.setVisible(true);
        });
    }

    public List<ActionListener> getGuiListeners() {
        return guiListeners;
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
        GameMenuListener gameMenuListener = new GameMenuListener(menuButtonNames);
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
