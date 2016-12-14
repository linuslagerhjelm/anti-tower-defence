package view.listeners;

import controller.eventhandler.Observable;
import controller.eventhandler.Observer;
import view.PopUpMenu;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

/**
 * Author: Andreas, Arvid
 * File: InfoMenuListener
 * Description: Action listener for the information menu with
 * the subscribe/observer design.
 */
public class InfoMenuListener implements ActionListener, Observable {

    private PopUpMenu highscores;
    private String[] buttonNames;
    private List<Observer> observers = new ArrayList<>();

    public InfoMenuListener(String[] inButtonNames){

        buttonNames = inButtonNames;
    }

    public PopUpMenu getHighScoreMenu() {
        return highscores;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        //About Button pressed.
        if (e.getActionCommand().equals(buttonNames[0])) {

            //About PopUp.
            new PopUpMenu("About",
                    "This is a Anti Tower defence game created for the umeå\n" +
                            "university as a project for the java application course\n\n " +
                            "This is the first version created, and it was created " +
                            "in 2016-12-15\n\n" +
                            "It was programmed by students of computer science and \n" +
                            "Interaction design using java swing api.\n"
                    , 400, 200);

        //Help Button pressed.
        } else if (e.getActionCommand().equals(buttonNames[1])) {

            new PopUpMenu("Help",
            "To start a new game press the \"Main Menu\" then \"new game\". \n " +
            "Then you should choose troops to send from the troop maker panel to the right." +
            "By pressing the spawn button the chosen troop will be spawned." +
            "You can see the cost and other information about the chosen troop in the information panel below the troop icon.", 400, 200);

        } else if (e.getActionCommand().equals(buttonNames[2])) {
            highscores = new PopUpMenu("Highscores","Level\t Time\t Score\n" , 300, 300);
            notifyObservers(e);
        } else {
            System.out.print("Did not find that button\n");
        }
    }


    @Override
    public void registerObserver(Observer observer) {
        observers.add(observer);
    }

    @Override
    public void unregisterObserver(Observer observer) {
        observers.remove(observer);
    }

    private void notifyObservers(ActionEvent e) {
        observers.forEach(observer -> observer.update(this, e));
    }
}