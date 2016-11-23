package view;

import javax.swing.*;
import java.awt.*;
//import Images.*;

/**
 * Created by c15aen on 2016-11-23.
 */
public class TroopMakerPanel {

        JButton spawnTroop;
        JButton nextTroop;
        JButton prevTroop;

        JTextArea troopInfroArea;
        JPanel troopMakerPanel = new JPanel();

        public TroopMakerPanel(int width, int height, String menuName) {

                ImageIcon troopIcon = new ImageIcon("/home/c15/c15aen/Downloads/Java/anti-tower-defence/AntiTowerIntelij/src/troopIcon.png");
                JLabel label = new JLabel();
                label.setBounds(0, 0, width, height);
                label.setIcon(troopIcon);

                troopMakerPanel.setBorder(BorderFactory.createTitledBorder("Fiskaktewbg"));
                troopMakerPanel.setMinimumSize(new Dimension(500,500));
                troopMakerPanel.setLayout(new BorderLayout());
                troopMakerPanel.add(label,BorderLayout.CENTER);
        }

        public JPanel returnPanel(){
                return troopMakerPanel;
        }
}
