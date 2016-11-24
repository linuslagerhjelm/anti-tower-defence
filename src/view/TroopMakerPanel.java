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

                ImageIcon troopIcon = new ImageIcon("/home/c15/c15aen/Downloads/Java/anti-tower-defence/AntiTowerIntelij/src/troopIcon.png");
                JLabel label = new JLabel();
                spawnTroop = new JButton();
                nextTroop = new JButton();
                prevTroop = new JButton();

                label.setBounds(0, 0, width, height);
                label.setIcon(troopIcon);

                troopMakerPanel.setBorder(BorderFactory.createTitledBorder("Fiskaktewbg"));
                troopMakerPanel.setMinimumSize(new Dimension(500,500));
                troopMakerPanel.setLayout(new GridLayout());
                troopMakerPanel.add(label);
                troopMakerPanel.add(spawnTroop, BorderLayout.EAST);
                troopMakerPanel.add(nextTroop, BorderLayout.SOUTH);
                troopMakerPanel.add(prevTroop, BorderLayout.NORTH);
        }

        public JPanel returnPanel(){

                return troopMakerPanel;
        }
}
