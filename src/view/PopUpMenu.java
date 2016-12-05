package view;

import javax.swing.*;
import java.awt.*;

/**
 * Created by AnonM on 2016-12-01.
 */
public class PopUpMenu {
    private JFrame frame;
    private JTextArea textArea;

    public PopUpMenu(String title, String message, int width, int height){
	frame = new JFrame(title);
	frame.setLayout(new BorderLayout());
	frame.setMinimumSize(new Dimension(width,height));
	frame.setMaximumSize(new Dimension(width,height));
	frame.setPreferredSize(new Dimension(width, height));
	textArea = new JTextArea();

	textArea.setText(message);
	textArea.setEditable(false);

	//Build panels
	JPanel centerPanel = new JPanel(new BorderLayout());
	centerPanel.add(textArea);

	//Add panels to the frame
	frame.add(centerPanel, BorderLayout.CENTER);
	frame.setLocationRelativeTo(null);
	frame.pack();
	frame.setVisible(true);
    }

    public void setMessage(String message){
	textArea.setText(message);
    }

    public void appendMessage(String message){
	textArea.append(message);
    }
}
