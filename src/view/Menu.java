package view;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * Created by c15aen on 2016-11-24.
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

    public JMenuItem createButton(String s) {
        return new JMenuItem(s);
    }

    public void setActionCommand(int index, String command) {

        buttons.get(index).setActionCommand(command);
    }

    public JMenu getJMenu() {

        return menu;
    }

    public ArrayList returnButtons() {

        return buttons;
    }
}
