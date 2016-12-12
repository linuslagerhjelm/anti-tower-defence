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

    private final TroopMakerPanel panel;
    private int currentIconIndex = 0;
    private static List<Observer> observers = new ArrayList<>();

    public TroopMakerListener(TroopMakerPanel panel) {
        this.panel = panel;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("next")) {
            panel.refresh();

            if (currentIconIndex == panel.getIconListSize()-1){

                panel.setTroopImage(0);
                currentIconIndex = 0;

            } else {
                currentIconIndex++;
                panel.setTroopImage(currentIconIndex);
            }


        } else if (e.getActionCommand().equals("prev")){
            panel.refresh();

            if (currentIconIndex == 0) {
                panel.setTroopImage(panel.getIconListSize()-1);
                currentIconIndex = panel.getIconListSize()-1;

            } else  {
                currentIconIndex--;
                panel.setTroopImage(currentIconIndex);
            }
        }

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
