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
    int level_origo_X = 0;
    int level_origo_Y = 0;
    int sprite_origo_x = 0;
    int sprite_origo_y = 0 ;

    public GameScreenPanel() {

        createTroop("troop_v2", new Position(0,0));
        createTroop("troop_v2", new Position(400,400));
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

        level_origo_X = (gameScreen.getWidth()-levelImage.getWidth())/2;
        level_origo_Y = (gameScreen.getHeight()-levelImage.getHeight())/2;

        g2d.drawImage(levelImage,level_origo_X,level_origo_Y,null);
    }

    public void drawSprites(Graphics g){
        Graphics2D g2d = (Graphics2D) g;

        //Makes rendering smooth
        RenderingHints rh = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        rh.put(RenderingHints.KEY_RENDERING,RenderingHints.VALUE_RENDER_QUALITY);
        g2d.setRenderingHints(rh);

        for(int i = 0; i < sprites.size(); i ++){

            sprite_origo_x = level_origo_X-(sprites.get(i).getImage().getWidth()/2);
            sprite_origo_y = level_origo_Y-(sprites.get(i).getImage().getHeight()/2);

            g2d.drawImage(sprites.get(i).getImage(),
                    sprite_origo_x+(int)sprites.get(i).getPos().getX(),
                    sprite_origo_y+(int)sprites.get(i).getPos().getY(),null);
        }
    }
}

