package view;

import javax.swing.*;
import java.awt.*;
//import Images.*;

/**
 * Created by c15aen on 2016-11-23.
 */
public class TroopMakerPanel {

        private JButton spawnTroop;
        private JButton nextTroop;
        private JButton prevTroop;

        private JTextArea troopInfroArea;
        private JPanel troopMakerPanel = new JPanel();

        public TroopMakerPanel(int width, int height, String menuName) {

                ImageIcon troopIcon = new ImageIcon("/home/c15/c15aen/Downloads/Java/anti-tower-defence/res/images/soldier.jpg");
                JLabel label = new JLabel();
                spawnTroop = new JButton("spawn");
                nextTroop = new JButton("next");
                prevTroop = new JButton("prev");

                label.setBounds(0, 0, width, height);
                label.setIcon(troopIcon);

                troopMakerPanel.setBorder(BorderFactory.createTitledBorder("Fiskaktewbg"));
                troopMakerPanel.setMinimumSize(new Dimension(50,50));
                troopMakerPanel.setLayout(new BorderLayout());
                troopMakerPanel.add(label, BorderLayout.WEST);
                troopMakerPanel.add(spawnTroop, BorderLayout.EAST);
                troopMakerPanel.add(nextTroop, BorderLayout.SOUTH);
                troopMakerPanel.add(prevTroop, BorderLayout.NORTH);
        }

        public JPanel returnPanel(){

                return troopMakerPanel;
        }
}

