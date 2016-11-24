package view;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * Created by c15aen on 2016-11-24.
 */
public class Menu {

        //private JMenuItem restartLevelButton;
        private ArrayList<JMenuItem> buttons = new ArrayList<JMenuItem>();
        private JMenu menu;

        public Menu(String[] buttonNames, String menuName) {
                menu = new JMenu(menuName);
                for(int i = 0; i < buttonNames.length; i++) {
                        JMenuItem newButton = createButton(buttonNames[i]);
                        buttons.add(i,newButton);
                        menu.add(newButton);
                }
        }

        private JMenuItem createButton(String s) {
                return new JMenuItem(s);
        }

        public void enableButton(int index, boolean enable){
                buttons.get(index).setEnabled(enable);
        }

        public void setButtonAction(int index, ActionListener al){
                buttons.get(index).addActionListener(al);
        }

        public JMenu getMenu(){
                return menu;
        }
}
