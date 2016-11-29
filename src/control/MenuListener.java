package control;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by AnonM on 2016-11-25.
 *
 * Actionlisiner that listens to the game menu buttons being pressed.
 *
 */


public class MenuListener implements ActionListener {

    private String[] buttonNames;
    public MenuListener(String[] inButtonNames){
        buttonNames = inButtonNames;
    }

    public void getActions(){

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand() == buttonNames[0]){
            System.out.print("Button detected: " + e.getActionCommand() + " \n");
        }else if (e.getActionCommand() == buttonNames[1]){
            System.out.print("Button detected: " + e.getActionCommand() + " \n");
        }else{
            System.out.print("Did not find that button\n");
        }
    }
}
