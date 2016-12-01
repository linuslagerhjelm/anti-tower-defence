package view;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;


/** // this is hereasd
 * Created by c15aen on 2016-11-23. not default layout
 */
public class GameScreenPanel {

    private JPanel gameScreen;
    private BufferedImage troopImage = null;

    public GameScreenPanel() {

	createImage();

	try{
	    gameScreen = new JPanel(){
		@Override
		protected void paintComponent(Graphics g) {
		    super.paintComponent(g);
		    g.drawImage(troopImage, 0, 0, null);
		}
	    };

	}catch (Exception p){
	    System.out.print("" + p.getCause());
	}
	Graphics g2 = troopImage.createGraphics();
	g2.drawImage(troopImage, 100, 100, null);

	gameScreen.setBackground(Color.BLACK);
    }

    public JPanel getGameScreen() {
	return gameScreen;
    }

    public JPanel getJPanel(){
	return gameScreen;
    }

    public void createImage() {
	try {
	    troopImage = ImageIO.read(new File("./res/images/troop.jpg"));
	} catch (Exception e) {
	    System.out.print("Exception");
	}
    }

    //BufferedImage backGround = new BufferedImage(200,200,BufferedImage.TYPE_INT_RGB);
    //Graphics g = bufferedImage.getGraphics();

}
