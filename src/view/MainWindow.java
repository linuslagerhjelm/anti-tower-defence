package view; /**
 * Created by c15aen on 2016-11-03. teset seteste
 */

import control.GameMenuListener;
import control.InfoMenuListener;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.lang.reflect.InvocationTargetException;

import java.util.ArrayList;
import java.util.List;

/**
 * view.MainWindow creates a simple GUI consisting of three panels. Largely based on the class ThreePanels by Johan Eliasson.
 */
public class MainWindow {

    private JFrame frame;
    private MenuPanel menuPanel;
    private TroopMakerPanel troopMakerPanel;
    private GameScreenPanel gameScreenPanel;
    private InfoPanel infoPanel;
    private List<ActionListener> guiListeners = new ArrayList<>();

    private static MainWindow mainWindowInstance = null;

    private MainWindow() {
        // Exists only to defeat instantiation.
    }

    public static MainWindow getInstance() {
        if(mainWindowInstance == null) {
            mainWindowInstance = new MainWindow("Fiskare",800,600);
        }
        return mainWindowInstance;
    }

    private MainWindow(String title, int width, int height) {
        SwingUtilities.invokeLater(() -> {
            frame = new JFrame(title);
            frame.setLayout(new BorderLayout());
            frame.setMinimumSize(new Dimension(width,height));
            frame.setMaximumSize(new Dimension(width,height));
            frame.setPreferredSize(new Dimension(width, height));
            frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

            troopMakerPanel = new TroopMakerPanel();
            menuPanel = new MenuPanel(50,50);
            infoPanel = new InfoPanel();
            gameScreenPanel = new GameScreenPanel();

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

            // Just tests!
            infoPanel.setMoney(500);
            infoPanel.setHighScore(1254363);
            infoPanel.addMoney(20);
            infoPanel.displayInfo();

            //loadImages();
            frame.setLocationRelativeTo(null);
            frame.pack();
        });
    }

    public List<ActionListener> getGuiListeners() {
        return guiListeners;
    }

    private void loadImages(){
        String[] troopIcons = {
                "soldier.jpg",
                "knight.jpeg",
                "3dSoldier.jpg",
                "spearSoldier.jpg"
        };
        troopMakerPanel.loadImages(troopIcons);
    }

    public void drawTroop(int x, int y){
        frame.repaint();
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

    public void changeUnitIcon(int index){

        troopMakerPanel.setTroopImage(index);
    }

    public int getTroopIcon_CurrentFrameIndex(){

        return troopMakerPanel.getCurrentImage();
    }

    public int getTroopIcon_ListSize(){

        return troopMakerPanel.getIconListSize();
    }


    private void setupGameMenu(){
        String[] menuButtonNames = {"New Game","Restart Level","Pause","Quit"};
        GameMenuListener gameMenuListener = new GameMenuListener(menuButtonNames);
        menuPanel.createMenu(menuButtonNames, "Main Menu", gameMenuListener);
    }

    private void setupInfoMenu(){
        String[] infoButtonNames = {"About","Help", "Highscores"};
        InfoMenuListener infoMenuListener = new InfoMenuListener(infoButtonNames);
        menuPanel.createMenu(infoButtonNames, "Info", infoMenuListener);
    }

    /**
     * Sets the GUI to be visible.
     */
    public void setVisible() {
        SwingUtilities.invokeLater(() -> {
            frame.setVisible(true);
        });
    }
}
