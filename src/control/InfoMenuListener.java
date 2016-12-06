package control;

import view.PopUpMenu;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by c15awl on 2016-11-28.
 */
public class InfoMenuListener implements ActionListener {

        private String[] buttonNames;

        public InfoMenuListener(String[] inButtonNames){
                buttonNames = inButtonNames;
        }

        public void getActions(){

        }

        @Override
        public void actionPerformed(ActionEvent e) {
                if (e.getActionCommand() == buttonNames[0]){
                        new PopUpMenu("About","This controller.game is a Anti tower defence controller.game.\n Made by masters of programming at umeå university\n The End\n",300,300);
                }else if (e.getActionCommand() == buttonNames[1]){
                        new PopUpMenu("Help","Use cheat code: 1337\n It will give you great power\n The End\n",300,300);
                }else{
                        System.out.print("Did not find that button\n");
                }
        }
}