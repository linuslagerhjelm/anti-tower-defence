package view; /**
 * Created by c15aen on 2016-11-03.
 */

import javax.swing.*;
import java.awt.*;

/**
 * view.MainWindow creates a simple GUI consisting of three panels. Largely based on the class ThreePanels by Johan Eliasson.
 */
public class MainWindow {

        private JFrame frame;

        private JButton runTestButton;
        private JButton clearButton;
        private JTextField textField;


        //Should only be called on EDT
        public MainWindow(String title, int width, int height) {
                frame = new JFrame(title);
                frame.setLayout(new BorderLayout());
                frame.setMinimumSize(new Dimension(width,height));
                frame.setMaximumSize(new Dimension(width,height));
                frame.setPreferredSize(new Dimension(width, height));
                frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

                MenuPanel menuPanel = new MenuPanel(50,50,"Huvudmeny");
                TroopMakerPanel troopMakerPanel = new TroopMakerPanel(12,21,"Fiskar");

                // Build panels
                JPanel upperPanel = menuPanel.returnPanel();
                //JPanel lowerPanel = infoPanel.returnPanel();
                //JPanel middlePanel = playFieldPanel.returPanel();
                JPanel rightPanel = troopMakerPanel.returnPanel();


                //Add panels to the frame
                frame.add(upperPanel, BorderLayout.NORTH);
                //frame.add(middlePanel, BorderLayout.CENTER);
                //frame.add(lowerPanel, BorderLayout.SOUTH);
                frame.add(rightPanel, BorderLayout.EAST);

                frame.pack();

        }

        //Should only be called on EDT

        /**
         * Sets the GUI to be visible.
         */
        public void show() {

                frame.setVisible(true);
        }

        /**
         *
         * @return A panel with flow layout and a "clear" button.
         *//*

        private JPanel buildLowerPanel() {
                JPanel lowerPanel = new JPanel();
                lowerPanel.setLayout(new FlowLayout(FlowLayout.CENTER));

                clearButton = new JButton("Clear");
                lowerPanel.add(clearButton);


                return lowerPanel;
        }

        *//**
         *
         * @return a panel containing a titled border, a text area and two buttonlisteners.
         *//*

        private JPanel buildMiddlePanel() {
                JTextArea textArea;
                JPanel middlePanel = new JPanel();
                middlePanel.setBorder(BorderFactory.createTitledBorder("Result"));
                middlePanel.setLayout(new BorderLayout());
                textArea = new JTextArea(25, 70);

                runTestButton.addActionListener(new ButtonListener(textField, textArea));
                clearButton.addActionListener(new ButtonListener(textField, textArea));

                textArea.setEditable(false);
                middlePanel.add(textArea);

                return middlePanel;
        }

        *//**
         *
         * @return a panel with flow layout containing a "run tests" button.
         *//*

        private JPanel buildUpperPanel() {
                JPanel upperPanel = new JPanel();
                upperPanel.setLayout(new FlowLayout(FlowLayout.CENTER));

                textField = new JTextField(35);
                upperPanel.add(textField, BorderLayout.CENTER);

                runTestButton = new JButton("Run tests");
                upperPanel.add(runTestButton);

                return upperPanel;
        }
*/
}
