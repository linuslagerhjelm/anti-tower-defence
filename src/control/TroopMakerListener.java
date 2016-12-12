package control;

import controller.eventhandler.Observable;
import controller.eventhandler.Observer;
import view.MainWindow;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by c15aen on 2016-11-30.
 */
public class TroopMakerListener implements ActionListener, Observable {

    private String buttonName;
    private static MainWindow myWindow = null;
    private int x = 0;
    private int y = 0;
    private int currentIconIndex = 0;
    private static List<Observer> observers = new ArrayList<>();

    public TroopMakerListener(String inButton) {
        buttonName = inButton;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        notifyObservers(e);
        if (e.getActionCommand().equals("next")){
            x=2;
            y=2;
            myWindow.drawTroop(x,y);

            if (currentIconIndex == myWindow.getTroopIcon_ListSize()-1){

                myWindow.changeUnitIcon(0);
                currentIconIndex = 0;

            }
            else {
                currentIconIndex++;
                myWindow.changeUnitIcon(currentIconIndex);
            }


        }else if(e.getActionCommand().equals("prev")){
            x=-2;
            y=-2;
            myWindow.drawTroop(x,y);

            if (currentIconIndex == 0){
                myWindow.changeUnitIcon(myWindow.getTroopIcon_ListSize()-1);
                currentIconIndex = myWindow.getTroopIcon_ListSize()-1;
            }

            else  {
                currentIconIndex--;
                myWindow.changeUnitIcon(currentIconIndex);
            }


        }else if(e.getActionCommand().equals("spawn")){
            if (myWindow == null){
                myWindow = MainWindow.getInstance();
            }

        }else {
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

    private void notifyObservers(Object action) {
        observers.forEach(observer -> observer.update(this, action));
    }
}
