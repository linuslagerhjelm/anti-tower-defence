package control;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by c15awl on 2016-11-28.
 */
public class GameMenuListener implements ActionListener {

    private String[] buttonNames;

    public GameMenuListener(String[] inButtonNames) {
        buttonNames = inButtonNames;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        //New Game pressed.
        if (e.getActionCommand() == buttonNames[0]){
            System.out.print("Button detected: " + e.getActionCommand() + " \n");
        }

        //Restart Level pressed.
        else if (e.getActionCommand() == buttonNames[1]){
            System.out.print("Button detected: " + e.getActionCommand() + " \n");

        }

        //Pause pressed.
        else if (e.getActionCommand() == buttonNames[2]){
            System.out.print("Button detected: " + e.getActionCommand() + " \n");

        }

        //Quit pressed.
        else if (e.getActionCommand() == buttonNames[3]){
            System.out.print("Button detected: " + e.getActionCommand() + " \n");

        }else if (e.getActionCommand() == buttonNames[4]){
            System.out.print("Button detected: " + e.getActionCommand() + " \n");

        }else{
            System.out.print("Did not find that button\n");
        }
    }
}

