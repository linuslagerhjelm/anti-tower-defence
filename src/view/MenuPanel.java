package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * Created by c15aen on 2016-11-23.
 */
public class MenuPanel {

    private JMenuBar menuBar = new JMenuBar();
    private JPanel menuPanel;
    private ArrayList<Menu> menus = new ArrayList<>();

    public MenuPanel(int width, int height) {

        menuPanel = new JPanel();
        menuPanel.setSize(width, height);
        menuPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        menuPanel.add(menuBar);

    }

    public void createMenu(String[] inString, String menuName, ActionListener al) {
        Menu newMenu = new Menu(inString, menuName, al);
        menuBar.add(newMenu.getJMenu());
        menus.add(newMenu);
        configButtonCommand(inString, newMenu);
    }

    private void configButtonCommand(String[] inString, Menu inMenu) {
        for (int i = 0; i < inString.length; i++)
            inMenu.setActionCommand(i, inString[i]);
    }


    public JPanel returnPanel() {

        return menuPanel;
    }

}