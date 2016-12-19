package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * File: MenuPanel
 * Created: 16-11-23
 * Description: MenuPanel that holds the menus at the top of the screen.
 *
 * @author Andreas
 * @author Arvid
 * @version 2.1
 */
public class MenuPanel {

    private JMenuBar menuBar = new JMenuBar();
    private JPanel menuPanel;
    private ArrayList<Menu> menus = new ArrayList<>();

    /**
     * Constructor for default menuPanel.
     *
     * @param width:int size of width.
     * @param height:int size of height.
     */
    public MenuPanel(int width, int height) {

        menuPanel = new JPanel();
        menuPanel.setSize(width, height);
        menuPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        menuPanel.add(menuBar);

    }

    /**
     * Creats a menu for the menupanel.
     *
     * @param inString:String[] name of JMenu Items to be used.
     * @param menuName:String Name of menu
     * @param al:ActionListener to be used by tyhe buttons.
     */
    public void createMenu(String[] inString, String menuName, ActionListener al) {
        Menu newMenu = new Menu(inString, menuName, al);
        menuBar.add(newMenu.getJMenu());
        menus.add(newMenu);
        configButtonCommand(inString, newMenu);
    }

    /**
     * Sets the action command of the buttons created.
     *
     * @param inString:String[], name of buttons to be used as command.
     * @param inMenu:String, name of menu.
     */
    private void configButtonCommand(String[] inString, Menu inMenu) {
        for (int i = 0; i < inString.length; i++)
            inMenu.setActionCommand(i, inString[i]);
    }


    /**
     * Returns the menuPanel as a JPanel.
     *
     * @return menuPanel:JPane
     */
    public JPanel returnPanel() {

        return menuPanel;
    }

}