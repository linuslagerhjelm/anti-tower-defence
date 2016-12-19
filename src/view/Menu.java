package view;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * File: Menu
 * Created: 16-11-23
 * Description: Menu buttons for the menu panel
 *
 * @author Andreas
 * @author Arvid
 * @version 2
 */
public class Menu {

    private ArrayList<JMenuItem> buttons = new ArrayList<>();
    private JMenu menu;

    public Menu(String[] buttonNames, String menuName, ActionListener a) {
        menu = new JMenu(menuName);
        for (int i = 0; i < buttonNames.length; i++) {
            JMenuItem newButton = createButton(buttonNames[i]);
            newButton.addActionListener(a);
            buttons.add(i, newButton);
            menu.add(newButton);
        }
    }

    /**
     * Creats a button of type JMenuItem in the menu.
     * @param s:string, name of button.
     * @return JMenuItems
     */
    public JMenuItem createButton(String s) {
        return new JMenuItem(s);
    }

    /**
     * Sets the action command for the specified button at index to the
     * given command.
     *
     * @param index:int for the position of action command to use
     * @param command:String the name of action command.
     */
    public void setActionCommand(int index, String command) {
        buttons.get(index).setActionCommand(command);
    }

    /**
     * Returns a JMenu component of the menu.
     *
     * @return menu:JMenu
     */
    public JMenu getJMenu() {

        return menu;
    }

    /**
     * Retuns the list of buttons from the array list.
     *
     * @return buttons:JMenuItem
     */
    public ArrayList returnButtons() {

        return buttons;
    }
}
