package view.listeners;

import controller.eventhandler.Observable;
import controller.eventhandler.Observer;

import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;

/**
 * Author: Linus Lagerhjelm
 * File: MouseEventListener
 * Created: 2016-12-14
 * Description:
 */
public class MouseEventListener implements MouseListener, Observable {
    private List<Observer> observers = new ArrayList<>();

    @Override
    public void mouseClicked(MouseEvent e) {
        notifyObservers(new ActionEvent(e.getSource(), e.getID(), e.paramString()));
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
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
