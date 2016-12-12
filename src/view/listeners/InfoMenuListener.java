package view.listeners;

import controller.eventhandler.Observable;
import controller.eventhandler.Observer;
import view.PopUpMenu;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by c15awl on 2016-11-28.
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
                if (e.getActionCommand().equals(buttonNames[0])) {
                        new PopUpMenu("About", "This controller.game is a Anti tower defence controller.game.\n Made by masters of programming at umeå university\n The End\n", 300, 300);
                } else if (e.getActionCommand().equals(buttonNames[1])) {
                        new PopUpMenu("Help", "Use cheat code: 1337\n It will give you great power\n The End\n", 300, 300);
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