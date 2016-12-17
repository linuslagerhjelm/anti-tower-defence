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

    public InfoMenuListener(String[] inButtonNames) {

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
                    , 450, 250);

            //Help Button pressed.
        } else if (e.getActionCommand().equals(buttonNames[1])) {

            new PopUpMenu("Help",
                    "To start a new game press the \"Main Menu\" then \"new game\". \n\n " +
                            "Then you should choose troops to send from the troop maker \n " +
                            "panel to the right. \n\nBy pressing the spawn button the chosen \n" +
                            "troop will be spawned. \n\n You can see the cost and other information\n" +
                            "about the chosen troop in the information panel below the troop icon.\n\n\n", 450, 250);

        } else if (e.getActionCommand().equals(buttonNames[2])) {
            highscores = new PopUpMenu("Highscores", "Level\t Time\t Score\n", 300, 300);
            notifyObservers(e);
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