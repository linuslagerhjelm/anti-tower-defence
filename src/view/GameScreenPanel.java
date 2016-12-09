package view;

import model.level.Position;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.Line2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


/** // this is hereasdasd
 * Created by c15aen on 2016-11-23. not default layout
 */
public class GameScreenPanel {

        private JPanel gameScreen;
        private BufferedImage levelImage = null;
        private BufferedImage troopImage = null;
        private BufferedImage teleportPadImage = null;
        private Map<Integer, Sprite> sprites = new ConcurrentHashMap<>();
        private List<Line2D.Double> lasers = new ArrayList<>();
        int level_origo_X = 0;
        int level_origo_Y = 0;
        int sprite_origo_x = 0;
        int sprite_origo_y = 0 ;
        int pad_origo_x = 0;
        int pad_origo_y = 0 ;

        public GameScreenPanel() {

                createTroop("troop_v2");
                //createTroop("troop_v2", new Position(400,400));
                createLevel("defaultLevel");
                createTeleportPad();


                try{
                        gameScreen = new JPanel(){
                                @Override
                                protected void paintComponent(Graphics g) {
                                        super.paintComponent(g);
                                        drawLevel(g);
                                        drawSprites(g);
                                        drawLasers(g);
                                        //drawTeleportPad(g, new Position(100,100));

                                        sprites.clear();
                                        lasers.clear();
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

        public void drawTroop(double x, double y) {
                sprites.put((int) y, new Sprite(troopImage, x, y));
        }

        public void drawLaser(double x1, double y1, double x2, double y2) {
                lasers.add(new Line2D.Double(x1, y1, x2, y2));
        }

        public void createTroop(String troopName) {
                try {
                    troopImage = ImageIO.read(new File(getClass().getResource("/images/troops/" + troopName + ".png").getFile()));
                } catch (Exception e) {
                    System.out.print("Troop creation Exception\n");
                }
        }

        public void createLevel(String levelName) {
                try {
                        levelImage = ImageIO.read(new File(getClass().getResource("/images/levels/"+levelName+".jpg").getFile()));
                } catch (Exception e) {
                        System.out.print("Level creation Exception\n");
                }
        }
        public void createTeleportPad() {
                try {
                        teleportPadImage = ImageIO.read(new File(getClass().getResource("/images/levels/Portal.png").getFile()));
                } catch (Exception e) {
                        System.out.print("Pad creation Exception\n");
                }
        }

        public void drawLevel(Graphics g){
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


                // Draw sprites based on y position (key)
                List<Integer> keys = new ArrayList<>(sprites.keySet());
                keys.sort(Integer::compareTo);
                for (Integer key : keys) {
                    Sprite sprite = sprites.get(key);

                    sprite_origo_x = level_origo_X - (sprite.getImage().getWidth() / 2);
                    sprite_origo_y = level_origo_Y - (sprite.getImage().getHeight() / 2);

                    BufferedImage image = sprite.getImage();
                    g2d.drawImage(image,
                            sprite_origo_x + (int) sprite.getX(),
                            sprite_origo_y + (int) sprite.getY(), null);
                }
        }

        public void drawLasers(Graphics g) {
                Graphics2D g2d = (Graphics2D) g;

                //Makes rendering smooth
                RenderingHints rh = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                rh.put(RenderingHints.KEY_RENDERING,RenderingHints.VALUE_RENDER_QUALITY);
                g2d.setRenderingHints(rh);

                for (Line2D laser : lasers) {
                    g2d.drawLine((int) laser.getX1(), (int) laser.getY1(),
                                 (int) laser.getX2(), (int) laser.getY2());
                }

        }

        public void drawTeleportPad(Graphics g, Position padPosition) {
                Graphics2D g2d = (Graphics2D) g;

                //Makes rendering smooth
                RenderingHints rh = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                rh.put(RenderingHints.KEY_RENDERING,RenderingHints.VALUE_RENDER_QUALITY);
                g2d.setRenderingHints(rh);

                pad_origo_x = teleportPadImage.getWidth()/2;
                pad_origo_y = teleportPadImage.getHeight()/2;

                float rateX = (float)gameScreen.getWidth()/(float)teleportPadImage.getWidth();
                float rateY = (float)gameScreen.getHeight()/(float)teleportPadImage.getHeight();
                //rateX = rateX*1/10;
                //rateY = rateY*1/10;

                if (rateX>rateY){
                        int W=(int)(teleportPadImage.getWidth()*rateY);
                        int H=(int)(teleportPadImage.getHeight()*rateY);
                        g2d.drawImage(teleportPadImage,(int)padPosition.getX()-pad_origo_x,(int)padPosition.getX()-pad_origo_x,W,H,null);
                }
                else{
                        int W=(int)(teleportPadImage.getWidth()*rateX);
                        int H=(int)(teleportPadImage.getHeight()*rateX);
                        g2d.drawImage(teleportPadImage,(int)padPosition.getX()-pad_origo_x,(int)padPosition.getX()-pad_origo_x,W,H,null);
                }

                //g2d.drawImage(teleportPadImage, (int)padPosition.getX()-pad_origo_x, (int)padPosition.getX()-pad_origo_x,null);

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


        }*/

        /*public void drawSprites(Graphics g){
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

        /*public void drawLevel(Graphics g){
        Graphics2D g2d = (Graphics2D) g;

        //Makes rendering smooth
        RenderingHints rh = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        rh.put(RenderingHints.KEY_RENDERING,RenderingHints.VALUE_RENDER_QUALITY);
        g2d.setRenderingHints(rh);

        level_origo_X = (gameScreen.getWidth()-levelImage.getWidth())/2;
        level_origo_Y = (gameScreen.getHeight()-levelImage.getHeight())/2;

        g2d.drawImage(levelImage,level_origo_X,level_origo_Y,null);

        }*/
