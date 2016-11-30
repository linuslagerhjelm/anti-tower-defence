package control;

import view.MainWindow;
import view.TroopMakerPanel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


/**
 * Created by c15aen on 2016-11-30.
 */
public class TroopMakerListener implements ActionListener {

        private String buttonName;

        public TroopMakerListener(String inButton) {

                buttonName = inButton;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
                if (e.getActionCommand().equals("next")){
                        System.out.print("Button detected: " + e.getActionCommand() + " \n");

                }else if(e.getActionCommand().equals("prev")){
                        System.out.print("Button detected: " + e.getActionCommand() + " \n");

                }else if(e.getActionCommand().equals("spawn")){
                        System.out.print("Button detected: " + e.getActionCommand() + " \n");

                }else {
                        System.out.print("Did not find that button\n");
                }
        }
}
