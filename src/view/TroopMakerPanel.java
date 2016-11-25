package view;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * Created by c15aen on 2016-11-23.
 */
public class TroopMakerPanel {

        private JButton spawnTroop;
        private JButton nextTroop;
        private JButton prevTroop;
        private final int DEFAULT_IMAGE_INDEX = 0;

        private JLabel label;
        private JTextArea troopInfroArea;
        private JPanel troopMakerPanel = new JPanel();
        private ArrayList<ImageIcon> troopIcons = new ArrayList<>();

        public TroopMakerPanel(int width, int height) {
                label = new JLabel();

                //DefaultImage
                ImageIcon troopIcon = new ImageIcon("./res/images/soldier.jpg");
                troopIcons.add(DEFAULT_IMAGE_INDEX,troopIcon);

                label.setBounds(0, 0, width, height);

                setTroopImage(DEFAULT_IMAGE_INDEX);
                buttonSetup();
                panelSetup();

                troopMakerPanel.add(label, BorderLayout.NORTH);
        }

        private void buttonSetup(){
                Dimension buttonDimension = new Dimension(70,30);

                spawnTroop = new JButton("spawn");
                nextTroop = new JButton("next");
                prevTroop = new JButton("prev");

                prevTroop.setPreferredSize(buttonDimension);
                nextTroop.setPreferredSize(buttonDimension);
                spawnTroop.setPreferredSize(buttonDimension);
                spawnTroop.setMaximumSize(buttonDimension);
                nextTroop.setMaximumSize(buttonDimension);
                prevTroop.setMaximumSize(buttonDimension);
        }

        private void panelSetup(){

                troopMakerPanel.setBorder(BorderFactory.createTitledBorder("Unit Maker"));
                troopMakerPanel.setMinimumSize(new Dimension(50,50));
                troopMakerPanel.setLayout(new BorderLayout());
                troopMakerPanel.add(spawnTroop, BorderLayout.CENTER);
                troopMakerPanel.add(nextTroop, BorderLayout.EAST);
                troopMakerPanel.add(prevTroop, BorderLayout.WEST);
                JPanel southPanel = new JPanel();
                southPanel.setPreferredSize(new Dimension(25,320));
                troopMakerPanel.add(southPanel , BorderLayout.SOUTH);
        }

        public JPanel getJPanel(){
                return troopMakerPanel;
        }

        public void setTroopImage(int index){
                try {
                        label.setIcon(troopIcons.get(index));
                } catch (NullPointerException e){
                        System.out.print("Error, index out of bounds");
                }
        }

        public void addTroopImage(int index, ImageIcon image){
                try {
                        troopIcons.add(index,image);
                } catch (NullPointerException e){
                        System.out.print("Error, index out of bounds");
                }
        }

        public void getTroopImageList(){

        }
}

