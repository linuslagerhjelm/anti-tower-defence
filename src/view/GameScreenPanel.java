package view;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;


/** // this is hereasdasd
 * Created by c15aen on 2016-11-23. not default layout
 */
public class GameScreenPanel {

    private JPanel gameScreen;
    private BufferedImage troopImage = null;
    public int x, y;

    public GameScreenPanel() {
        x=10;
        y=10;
	    createImage();

        try{
            gameScreen = new JPanel(){
                @Override
                protected void paintComponent(Graphics g) {
                    super.paintComponent(g);
                    drawSoldier(g);
                    //g.drawImage(troopImage, 0, 0, null);
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
            troopImage = ImageIO.read(new File("./res/images/troops/troop.jpg"));
        } catch (Exception e) {
            System.out.print("Exception");
        }
    }


    public void drawSoldier(Graphics g){

        Graphics2D g2d = (Graphics2D) g;

        //Makes rendering smooth
        RenderingHints rh = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        rh.put(RenderingHints.KEY_RENDERING,RenderingHints.VALUE_RENDER_QUALITY);
        g2d.setRenderingHints(rh);

        //Get screen width and height
        Dimension size = gameScreen.getSize();
        double w = size.getWidth();
        double h = size.getHeight();

        g2d.drawImage(troopImage,x,y,null);
    }
    //BufferedImage backGround = new BufferedImage(200,200,BufferedImage.TYPE_INT_RGB);
    //Graphics g = bufferedImage.getGraphics();

}
