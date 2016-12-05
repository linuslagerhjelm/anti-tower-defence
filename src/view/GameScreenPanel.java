package view;

import level.Position;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;


/** // this is hereasdasd
 * Created by c15aen on 2016-11-23. not default layout
 */
public class GameScreenPanel {

    private JPanel gameScreen;
    private BufferedImage levelImage = null;
    private BufferedImage troopImage = null;
    private ArrayList<Sprite> sprites = new ArrayList();

    public int x, y;


    public GameScreenPanel() {

        createTroop("troop_v2", new Position(40,40));
        createTroop("troop_v2", new Position(0,0));
        createLevel("defaultLevel");

        try{
            gameScreen = new JPanel(){
                @Override
                protected void paintComponent(Graphics g) {
                    super.paintComponent(g);
                    drawLevel(g);
                    drawSprites(g);
                }
            };

        }catch (Exception p){
            System.err.print("" + p.getCause());
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

    public void createTroop(String troopName, Position spawnPosition) {
        try {
            troopImage = ImageIO.read(new File("./res/images/troops/"+troopName+".png"));
        } catch (Exception e) {
            System.out.print("Exception");
        }
        sprites.add(new Sprite(troopImage,spawnPosition));
    }

    public void createLevel(String levelName) {
        try {
            levelImage = ImageIO.read(new File("./res/images/levels/"+levelName+".jpg"));
        } catch (Exception e) {
            System.out.print("Exception");
        }
    }


    public void drawLevel(Graphics g){
        Graphics2D g2d = (Graphics2D) g;

        //Makes rendering smooth
        RenderingHints rh = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        rh.put(RenderingHints.KEY_RENDERING,RenderingHints.VALUE_RENDER_QUALITY);
        g2d.setRenderingHints(rh);

        Dimension screenSize = gameScreen.getSize();
        int origo_X = (int)(screenSize.getWidth()-levelImage.getWidth()/2);
        int origo_Y = (int)(screenSize.getHeight()-levelImage.getHeight()/2);

        g2d.drawImage(levelImage,0,0,origo_X,origo_Y,null);
    }

    public void drawSprites(Graphics g){
        Graphics2D g2d = (Graphics2D) g;

        //Makes rendering smooth
        RenderingHints rh = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        rh.put(RenderingHints.KEY_RENDERING,RenderingHints.VALUE_RENDER_QUALITY);
        g2d.setRenderingHints(rh);

        for(int i = 0; i < sprites.size(); i ++){
            g2d.drawImage(sprites.get(i).getImage(),
                    (int)sprites.get(i).getPos().getX(),
                    (int)sprites.get(i).getPos().getY(),null);
        }
    }
}


//BufferedImage backGround = new BufferedImage(200,200,BufferedImage.TYPE_INT_RGB);
//Graphics g = bufferedImage.getGraphics();
//    //Get screen width and height
//    Dimension size = gameScreen.getSize();
//    double w = size.getWidth();
//    double h = size.getHeight();

