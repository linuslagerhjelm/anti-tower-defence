package view; /**
 * Created by c15aen on 2016-11-03. teset seteste
 */

import control.GameMenuListener;
import control.MenuListener;

import javax.swing.*;
import java.awt.*;

/**
 * view.MainWindow creates a simple GUI consisting of three panels. Largely based on the class ThreePanels by Johan Eliasson.
 */
public class MainWindow {

    private static JFrame frame;
    private static MenuPanel menuPanel;
    private static TroopMakerPanel troopMakerPanel;
    private static GameScreenPanel gameScreenPanel;
    private static InfoPanel infoPanel;

    private static MainWindow mainWindowInstance = null;

    private MainWindow() {
        // Exists only to defeat instantiation.
    }

    public static MainWindow getInstance() {
        if(mainWindowInstance == null) {
            mainWindowInstance = new MainWindow("Fiskare",900,800);
        }
        return mainWindowInstance;
    }



    private MainWindow(String title, int width, int height) {
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


        frame.pack();

            /*for (int i = 0; i < 99999; i ++){
                gameScreenPanel.x++;
                gameScreenPanel.y++;
                frame.repaint();
            }*/

    }

    public void drawTroop(int x, int y){
        gameScreenPanel.x += x;
        gameScreenPanel.y += y;
        frame.repaint();
    }

    public static void changeUnitIcon(int index){
        troopMakerPanel.setTroopImage(index);
    }

    public static int getTroopIcon_CurrentFrameIndex(){
        return troopMakerPanel.getCurrentImage();
    }

    public static int getTroopIcon_ListSize(){
        return troopMakerPanel.getIconListSize();
    }


    private static void setupGameMenu(){
        String[] menuButtonNames = {"New Game","Restart Level","Pause","Quit"};
        GameMenuListener gameMenuListener = new GameMenuListener(menuButtonNames);
        menuPanel.createMenu(menuButtonNames, "Main Menu", gameMenuListener);

    }

    private static void setupInfoMenu(){
        String[] infoButtonNames = {"About","Help"};
        MenuListener infoMenuListener = new MenuListener(infoButtonNames);
        menuPanel.createMenu(infoButtonNames, "Info", infoMenuListener);
    }

    /**
     * Sets the GUI to be visible.
     */
    public static void setVisible() {
        frame.setVisible(true);
    }
}
