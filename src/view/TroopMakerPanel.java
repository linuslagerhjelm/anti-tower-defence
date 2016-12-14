package view;

import view.listeners.TroopMakerListener;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
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
        private ArrayList<ActionListener> actionListeners = new ArrayList<>();

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
                troopIcons.add(new ImageIcon("./res/images/troops/soldier.jpg"));
                troopIcons.add(new ImageIcon("./res/images/troops/knight.jpeg"));
                troopIcons.add(new ImageIcon("./res/images/troops/3dSoldier.jpg"));
                troopIcons.add(new ImageIcon("./res/images/troops/spearSoldier.jpg"));

                setTroopImage(0);
                panelSetup();
        }

        public void refresh() {
                troopIconPanel.repaint();
        }

        /**
         * Setups the buttons needed and configures their sizes.
         */
        private void buttonSetup(){
                Dimension buttonDimension = new Dimension(85,100);

                spawnTroop = new JButton("spawn");
                nextTroop = new JButton("next");
                prevTroop = new JButton("prev");

                TroopMakerListener spawnListener = new TroopMakerListener(this);
                actionListeners.add(spawnListener);

                prevTroop.setPreferredSize(buttonDimension);
                nextTroop.setPreferredSize(buttonDimension);
                spawnTroop.setPreferredSize(buttonDimension);
                spawnTroop.setMaximumSize(buttonDimension);
                nextTroop.setMaximumSize(buttonDimension);
                prevTroop.setMaximumSize(buttonDimension);

                prevTroop.addActionListener(spawnListener);
                nextTroop.addActionListener(spawnListener);
                spawnTroop.addActionListener(spawnListener);

                disableButtons();
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
                //changeUnitInfo("Speed: 20\nCost: 100\nHealth: 45");
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
                if (troopIcons.size() != 0) {
                        label.setIcon(troopIcons.get(index));
                }
        }

        public void changeUnitInfo(String unitInfo) {
                unitInfoText.setText(unitInfo);
        }

        public int getIconListSize() {
                return troopIcons.size();
        }

        public ArrayList<ActionListener> getActionListeners() {
                return actionListeners;
        }

        public void disableButtons() {
                spawnTroop.setEnabled(false);
                nextTroop.setEnabled(false);
                prevTroop.setEnabled(false);
        }

        public void enableButtons() {
                spawnTroop.setEnabled(true);
                nextTroop.setEnabled(true);
                prevTroop.setEnabled(true);
        }
}

