package view;

import com.sun.java.swing.plaf.motif.MotifBorders;
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
        private JPanel troopMakerPanel;
        private JPanel troopInfoPanel;
        private JPanel buttonPanel;
        private JPanel troopIconPanel;
        private JTextPane unitInfoText;

        public TroopMakerPanel() {
                label = new JLabel();
                troopMakerPanel = new JPanel();

                //Load DefaultImage
                ImageIcon soldierImage = new ImageIcon("./res/images/troops/soldier.jpg");
                ImageIcon knightImage = new ImageIcon("./res/images/troops/knight.jpeg");
                ImageIcon soldier3 = new ImageIcon("./res/images/troops/3dSoldier.jpg");
                ImageIcon soldier4 = new ImageIcon("./res/images/troops/spearSoldier.jpg");


                //Add the default image to the icon list and sets the current image to the default image.
                addTroopImage(0,soldierImage);
                addTroopImage(1,knightImage);
                addTroopImage(2,soldier3);
                addTroopImage(3,soldier4);

                setTroopImage(0);



                //Setups the panel.
                panelSetup();

                //Adds the image frame.
                //troopMakerPanel.add(label, BorderLayout.NORTH);
        }

        public void loadImages(String[] imagesPath){
                for(int i = 0; i < imagesPath.length; i ++) {
                        try{
                                addTroopImage(i,new ImageIcon(imagesPath[1]));
                        }catch (NullPointerException e){
                                e.printStackTrace();
                                System.out.print("Error, no thing");
                        }

                }

        }

        /**
         * Setups the buttons needed and configures their sizes.
         */
        private void buttonSetup(){
                Dimension buttonDimension = new Dimension(85,100);

                spawnTroop = new JButton("spawn");
                nextTroop = new JButton("next");
                prevTroop = new JButton("prev");

                prevTroop.setPreferredSize(buttonDimension);
                nextTroop.setPreferredSize(buttonDimension);
                spawnTroop.setPreferredSize(buttonDimension);
                spawnTroop.setMaximumSize(buttonDimension);
                nextTroop.setMaximumSize(buttonDimension);
                prevTroop.setMaximumSize(buttonDimension);

                prevTroop.addActionListener(new TroopMakerListener("prev"));
                nextTroop.addActionListener(new TroopMakerListener("next"));
                spawnTroop.addActionListener(new TroopMakerListener("spawn"));
        }

        /**
         * Setups the panel and adds all the button panels
         * to the Troop Maker Panel.
         */
        private void panelSetup(){

                troopInfoPanelSetup();
                buttonPanelSetup();
                troopIconPanelSetup();

                troopMakerPanel.setBorder(BorderFactory.createTitledBorder("Unit Maker"));
                troopMakerPanel.setMinimumSize(new Dimension(40,40));
                troopMakerPanel.setLayout(new BorderLayout());

                //
                troopMakerPanel.add(troopInfoPanel , BorderLayout.CENTER);
                troopMakerPanel.add(buttonPanel, BorderLayout.SOUTH);
                troopMakerPanel.add(troopIconPanel, BorderLayout.NORTH);
        }

        private void troopInfoPanelSetup() {
                troopInfoPanel = new JPanel();
                //troopInfoPanel.setPreferredSize(new Dimension(25,320));
                unitInfoText = new JTextPane();
                troopInfoPanel.add(unitInfoText);
                unitInfoText.setEditable(false);
                unitInfoText.setBackground(Color.GRAY);
                unitInfoText.setPreferredSize(new Dimension(220,319));
                unitInfoText.setBorder(BorderFactory.createTitledBorder("Info"));
                unitInfoText.setFont(new Font("Serif", Font.BOLD, 16));
                unitInfoText.setForeground(Color.CYAN);
                changeUnitInfo("Speed: 20\nCost: 100\nHealth: 45");


        }

        private void buttonPanelSetup() {
                buttonSetup();
                buttonPanel = new JPanel();

                //Adds all components.
                buttonPanel.add(prevTroop, BorderLayout.WEST);
                buttonPanel.add(spawnTroop, BorderLayout.CENTER);
                buttonPanel.add(nextTroop, BorderLayout.EAST);

        }

        private void troopIconPanelSetup() {
                troopIconPanel = new JPanel();
                troopIconPanel.add(label, BorderLayout.CENTER);
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

        public void changeUnitInfo(String unitInfo) {
                unitInfoText.setText(unitInfo);



        }

        public int getCurrentImage() {

                return currentImage;
        }

        public int getIconListSize() {

                return troopIcons.size();
        }

        //TODO: Add me ?
        public void getTroopImageList(){

        }

        //TODO: Am I needed?
        public void clearImageList(){
                troopIcons.clear();
        }
}

