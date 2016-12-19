package view;

import view.listeners.TroopMakerListener;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * File: TroopMakerPanel
 * Created: 16-11-23
 * Description: A panel object to create a TroopMaker JPanel for a frame.
 *
 * @author Andreas
 * @author Arvid
 */
public class TroopMakerPanel {

    //Buttons in the TroopMaker panel.
    private JButton spawnTroop;
    private JButton nextTroop;
    private JButton prevTroop;

    // Cache image icon load
    private HashMap<String, ImageIcon> troopIcons = new HashMap<>();
    private ArrayList<ActionListener> actionListeners = new ArrayList<>();

    private JLabel label;
    private JPanel troopMakerPanel;
    private JPanel troopInfoPanel;
    private JPanel buttonPanel;
    private JPanel troopIconPanel;
    private JTextPane unitInfoText;

    /**
     * Creates an instance of the troop maker panel.
     */
    public TroopMakerPanel() {
        label = new JLabel();
        troopMakerPanel = new JPanel();
        panelSetup();
    }

    /**
     * Setups the buttons needed and configures their sizes.
     */
    private void buttonSetup() {
        Dimension buttonDimension = new Dimension(85, 100);

        spawnTroop = new JButton("spawn");
        nextTroop = new JButton("next");
        prevTroop = new JButton("prev");

        TroopMakerListener spawnListener = new TroopMakerListener();
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
    private void panelSetup() {

        troopInfoPanelSetup();
        buttonPanelSetup();
        troopIconPanelSetup();

        troopMakerPanel.setBorder(BorderFactory.createTitledBorder("Unit Maker"));
        troopMakerPanel.setMinimumSize(new Dimension(40, 40));
        troopMakerPanel.setLayout(new BorderLayout());

        troopMakerPanel.add(troopInfoPanel, BorderLayout.CENTER);
        troopMakerPanel.add(buttonPanel, BorderLayout.SOUTH);
        troopMakerPanel.add(troopIconPanel, BorderLayout.NORTH);
    }

    /**
     * Setups the troop information panel.
     */
    private void troopInfoPanelSetup() {
        troopInfoPanel = new JPanel();
        //troopInfoPanel.setPreferredSize(new Dimension(25,320));
        unitInfoText = new JTextPane();
        troopInfoPanel.add(unitInfoText);
        unitInfoText.setEditable(false);
        unitInfoText.setBackground(Color.GRAY);
        unitInfoText.setPreferredSize(new Dimension(220, 319));
        unitInfoText.setBorder(BorderFactory.createTitledBorder("Info"));
        unitInfoText.setFont(new Font("Serif", Font.BOLD, 16));
        unitInfoText.setForeground(Color.CYAN);
        //changeUnitInfo("Speed: 20\nCost: 100\nHealth: 45");
    }

    /**
     * Setups the button panel for the troop maker setup.
     */
    private void buttonPanelSetup() {
        buttonSetup();
        buttonPanel = new JPanel();

        //Adds all components.
        buttonPanel.add(prevTroop, BorderLayout.WEST);
        buttonPanel.add(spawnTroop, BorderLayout.CENTER);
        buttonPanel.add(nextTroop, BorderLayout.EAST);
    }


    /**
     * Setups the icon panel.
     */
    private void troopIconPanelSetup() {
        troopIconPanel = new JPanel();
        troopIconPanel.add(label, BorderLayout.CENTER);
    }

    /**
     * Returns a JPanel of the TroopMaker panel.
     *
     * @return JPanel, returns a JPanel of the TroopMakerPanel.
     */
    public JPanel getJPanel() {

        return troopMakerPanel;
    }

    /**
     * Sets the current viewing troop image to the image from the
     * image list at the given index.
     *
     * @param path:int, index at the location of the wanted image.
     */
    public void setTroopImage(String path) {
        if (troopIcons.get(path) != null) {
            label.setIcon(troopIcons.get(path));
        } else {
            try {
                troopIcons.put(path, loadImage(path));
                label.setIcon(troopIcons.get(path));
            } catch (NullPointerException e) {
                troopIcons.put(path, null);
            }
        }
    }

    /**
     * Loads the images to the Icon list.
     *
     * @param path:int, index at the location of the wanted image.
     */
    private ImageIcon loadImage(String path) {
        return new ImageIcon(getClass().getResource(path));
    }

    /**
     * Sets the current viewing troop image to the image from the
     * image list at the given index.
     *
     * @param unitInfo:String, index at the location of the wanted image.
     */
    public void changeUnitInfo(String unitInfo) {
        unitInfoText.setText(unitInfo);
    }

    /**
     * Returns the actionlistiners being used.
     * @return actionListiners:ArrayList<ActionListener>
     */
    public ArrayList<ActionListener> getActionListeners() {
        return actionListeners;
    }

    /**
     * Disables the button at troop maker panel
     */
    public void disableButtons() {
        spawnTroop.setEnabled(false);
        nextTroop.setEnabled(false);
        prevTroop.setEnabled(false);
    }

    /**
     * Enables all buttons for the troop maker panel.
     */
    public void enableButtons() {
        spawnTroop.setEnabled(true);
        nextTroop.setEnabled(true);
        prevTroop.setEnabled(true);
    }
}

