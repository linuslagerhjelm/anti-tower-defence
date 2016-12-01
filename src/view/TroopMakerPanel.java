package view;

import control.TroopMakerListener;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * Author: Andreas, Arvid
 * File: TroopMakerPanel
 * Created: 16-11-23
 * Description: A panel object to create a TroopMaker JPanel for a frame.
 */
public class TroopMakerPanel {

        //Buttons in the TroopMaker panel.
        private JButton spawnTroop;
        private JButton nextTroop;
        private JButton prevTroop;

        //Sets the default image index to 0.
        private final int DEFAULT_IMAGE_INDEX = 0;
        private int currentImage = 0;

        //List of troopImages.
        private ArrayList<ImageIcon> troopIcons = new ArrayList<>();

        private JLabel label;
        private JPanel troopMakerPanel = new JPanel();

        public TroopMakerPanel() {
                label = new JLabel();

                //Load DefaultImage
                ImageIcon soldierImage = new ImageIcon("./res/images/soldier.jpg");
                ImageIcon knightImage = new ImageIcon("./res/images/knight.jpeg");

                //Add the default image to the icon list and sets the current image to the default image.
                addTroopImage(0,soldierImage);
                addTroopImage(1,knightImage);
                setTroopImage(currentImage);

                //Setups the buttons.
                buttonSetup();
                //Setups the panel.
                panelSetup();

                //Adds the image frame.
                troopMakerPanel.add(label, BorderLayout.NORTH);
        }

        /**
         * Setups the buttons needed and configures their sizes.
         */
        private void buttonSetup(){
                Dimension buttonDimension = new Dimension(70,200);

                spawnTroop = new JButton("spawn");
                nextTroop = new JButton("next");
                prevTroop = new JButton("prev");

                prevTroop.setPreferredSize(buttonDimension);
                nextTroop.setPreferredSize(buttonDimension);
                spawnTroop.setPreferredSize(buttonDimension);
                spawnTroop.setMaximumSize(buttonDimension);
                nextTroop.setMaximumSize(buttonDimension);
                prevTroop.setMaximumSize(buttonDimension);
                prevTroop.setMinimumSize(buttonDimension);
                prevTroop.addActionListener(new TroopMakerListener("prev"));
                nextTroop.addActionListener(new TroopMakerListener("next"));
                spawnTroop.addActionListener(new TroopMakerListener("spawn"));
        }

        /**
         * Setups the panel and adds all the button panels
         * to the Troop Maker Panel.
         */
        private void panelSetup(){
                JPanel southPanel = new JPanel();
                southPanel.setPreferredSize(new Dimension(25,320));

                troopMakerPanel.setBorder(BorderFactory.createTitledBorder("Unit Maker"));
                troopMakerPanel.setMinimumSize(new Dimension(40,40));
                troopMakerPanel.setLayout(new BorderLayout());

                //Adds all components.
                troopMakerPanel.add(spawnTroop, BorderLayout.CENTER);
                troopMakerPanel.add(nextTroop, BorderLayout.EAST);
                troopMakerPanel.add(prevTroop, BorderLayout.WEST);
                troopMakerPanel.add(southPanel , BorderLayout.SOUTH);
        }

        /**
         * Returns a JPanel of the TroopMaker panel.
         * @return JPanel, returns a JPanel of the TroopMakerPanel.
         */
        public JPanel getJPanel(){
                return troopMakerPanel;
        }


        /**
         * Sets the current viewing troop image to the image from the
         * image list at the given index.
         * @param index:int, index at the location of the wanted image.
         */
        public void setTroopImage(int index){
                try {
                        label.setIcon(troopIcons.get(index));
                } catch (NullPointerException e){
                        System.out.print("Error, index out of bounds");
                }
        }

        /**
         * Adds the given image to the Troop Image icon list at the given index.
         * @param index:int, index to store the given image.
         * @param image:ImageIcon, the image to be stored at the given index.
         */
        public void addTroopImage(int index, ImageIcon image){
                try {
                        troopIcons.add(index,image);
                } catch (NullPointerException e){
                        System.out.print("Error, index out of bounds");
                }
        }

        public int getCurrentImage() {
                return currentImage;
        }

        //TODO: Add me ?
        public void getTroopImageList(){

        }

        //TODO: Am I needed?
        public void clearImageList(){
                troopIcons.clear();
        }
}

