package view.listeners;

import controller.eventhandler.Observable;
import controller.eventhandler.Observer;
import view.MainWindow;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

/**
 * Author: Andreas, Arvid
 * File: GameMenuListener
 * Description: Action listener for the game menu with
 * the subscribe observer design.
 */
public class GameMenuListener implements ActionListener, Observable {
    private String[] buttonNames;
    private List<Observer> observers = new ArrayList<>();

    public GameMenuListener(String[] inButtonNames) {
        buttonNames = inButtonNames;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // New Game pressed.
        if (e.getActionCommand().equals(buttonNames[0])){
            if (((JMenuItem)e.getSource()).getText().equalsIgnoreCase("new game")) {
                notifyObservers(e);

                ((JMenuItem)e.getSource()).setText("Restart");

            } else {
                notifyObservers(new ActionEvent(new Object(), 0, "Restart"));
            }
        }

        // Pause/Resumed pressed.
        else if (e.getActionCommand().equals(buttonNames[1])){
            if (((JMenuItem)e.getSource()).getText().equalsIgnoreCase("pause")) {
                notifyObservers(e);
                ((JMenuItem)e.getSource()).setText("Resume");

            } else {
                ((JMenuItem)e.getSource()).setText("Pause");
                notifyObservers(new ActionEvent(new Object(), 0, "Resume"));
            }
        }

        // Quit was pressed
        else if (e.getActionCommand().equals(buttonNames[2])) {
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

