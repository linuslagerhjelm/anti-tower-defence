package view.listeners;

import controller.eventhandler.Observable;
import controller.eventhandler.Observer;
import view.TroopMakerPanel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by c15aen on 2016-11-30.
 */
public class TroopMakerListener implements ActionListener, Observable {

    private static List<Observer> observers = new ArrayList<>();

    public TroopMakerListener() {}

    @Override
    public void actionPerformed(ActionEvent e) {
        notifyObservers(e);
    }

    @Override
    public void registerObserver(Observer observer) {
        observers.add(observer);
    }

    @Override
    public void unregisterObserver(Observer observer) {
        observers.remove(observer);
    }

    private void notifyObservers(Object action) {
        observers.forEach(observer -> observer.update(this, action));
    }
}
