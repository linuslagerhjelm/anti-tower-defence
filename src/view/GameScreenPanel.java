package view;

import model.level.Position;

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
    private BufferedImage laserBeamImage = null;
    private ArrayList<Sprite> sprites = new ArrayList();
    int level_origo_X = 0;
    int level_origo_Y = 0;
    int sprite_origo_x = 0;
    int sprite_origo_y = 0 ;

    public GameScreenPanel() {

        //createTroop("troop_v2", new Position(0,0));
        //createTroop("troop_v2", new Position(400,400));
        createLevel("defaultLevel");
        //createLaserBeam("troop");

        try{
            gameScreen = new JPanel(){
                @Override
                protected void paintComponent(Graphics g) {
                    super.paintComponent(g);
                    drawLevel(g);
                    drawSprites(g);
                    drawLaser(g, new Position(12, 30), new Position(90,90));
                    sprites.clear();
                }
            };

        }catch (Exception p){
            System.err.print("" + p.getCause());
        }

        gameScreen.setBackground(Color.BLACK);
    }

    public JPanel getGameScreen() {
	    return gameScreen;
    }

    public void refresh() {
        SwingUtilities.invokeLater(() -> {
        gameScreen.repaint();
        });
    }
    public JPanel getJPanel(){

	    return gameScreen;
    }

    public void drawTroop(Position position) {
        createTroop("troop_v2", position);
    }

    public void createTroop(String troopName, Position spawnPosition) {
        SwingUtilities.invokeLater(() -> {
            try {
                troopImage = ImageIO.read(new File("./res/images/troops/"+troopName+".png"));
            } catch (Exception e) {
                System.out.print("Troop creation Exception\n");
            }
            sprites.add(new Sprite(troopImage,spawnPosition));
        });
    }

    public void createLevel(String levelName) {
        try {
            levelImage = ImageIO.read(new File("./res/images/levels/"+levelName+".jpg"));
        } catch (Exception e) {
            System.out.print("Level creation Exception\n");
        }
    }

    public void createLaserBeam(String laserpic) {
        try {
            laserBeamImage = ImageIO.read(new File("./res/images/troops/"+laserpic+".jpg"));
        } catch (Exception e) {
            System.out.print("Laser creation Exception\n");
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

            BufferedImage image = sprites.get(i).getImage();
            g2d.drawImage(image,
                    sprite_origo_x+(int)sprites.get(i).getPos().getX(),
                    sprite_origo_y+(int)sprites.get(i).getPos().getY(),null);
        }
    }

    public void drawLaser(Graphics g, Position startPos, Position endPos) {
        Graphics2D g2d = (Graphics2D) g;

        //Makes rendering smooth
        RenderingHints rh = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        rh.put(RenderingHints.KEY_RENDERING,RenderingHints.VALUE_RENDER_QUALITY);
        g2d.setRenderingHints(rh);

        g2d.drawLine((int)startPos.getX(), (int)startPos.getY(), (int)endPos.getX(), (int)endPos.getY());
    }

    public void removeLaser() {

    }
}

//BufferedImage backGround = new BufferedImage(200,200,BufferedImage.TYPE_INT_RGB);
//Graphics g = bufferedImage.getGraphics();
//    //Get screen width and height
//    Dimension size = gameScreen.getSize();
//    double w = size.getWidth();
//    double h = size.getHeight();



        //TODO Add this to make the gamescreen scalable.
        /*public void drawLevel(Graphics g){
                Graphics2D g2d = (Graphics2D) g;

                //Makes rendering smooth
                RenderingHints rh = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                rh.put(RenderingHints.KEY_RENDERING,RenderingHints.VALUE_RENDER_QUALITY);
                g2d.setRenderingHints(rh);


                float rateX =  (float)gameScreen.getWidth()/(float)levelImage.getWidth();
                float rateY = (float)gameScreen.getHeight()/(float)levelImage.getHeight();

                if (rateX>rateY){
                        int W=(int)(levelImage.getWidth()*rateY);
                        int H=(int)(levelImage.getHeight()*rateY);
                        g2d.drawImage(levelImage,0,0,W,H,null);
                }
                else{
                        int W=(int)(levelImage.getWidth()*rateX);
                        int H=(int)(levelImage.getHeight()*rateX);
                        g2d.drawImage(levelImage,0,0,W,H,null);
                }


        }

        public void drawSprites(Graphics g){
                Graphics2D g2d = (Graphics2D) g;

                //Makes rendering smooth
                RenderingHints rh = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                rh.put(RenderingHints.KEY_RENDERING,RenderingHints.VALUE_RENDER_QUALITY);
                g2d.setRenderingHints(rh);



                for(int i = 0; i < sprites.size(); i ++){
                        float rateX =  gameScreen.getWidth()/sprites.get(i).getImage().getWidth();
                        float rateY = gameScreen.getHeight()/sprites.get(i).getImage().getHeight();

                        if (rateX>rateY){
                                int W=(int)rateY;
                                int H=(int)rateY;
                                g2d.drawImage(sprites.get(i).getImage(),(int)sprites.get(i).getPos().getX()*W, (int)sprites.get(i).getPos().getY()*H,W,H,null);

                        }
                        else{
                                int W=(int)rateX;
                                int H=(int)rateX;
                                g2d.drawImage(sprites.get(i).getImage(),(int)sprites.get(i).getPos().getX()*W, (int)sprites.get(i).getPos().getY()*H,W,H,null);
                        }

                }
        }*/
