package view;

import model.level.Position;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.Line2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
* Author: Andreas, Arvid.
* File: GameScreenPanel
* Created: 2016-12-6
* Description: Game screen where the level, unit and etc will be displayed for the user.
*/
public class GameScreenPanel {

    private JPanel gameScreen;
    private BufferedImage levelImage = null;
    private BufferedImage troopImage = null;
    private BufferedImage teleportPadImage = null;
    private List<Sprite> sprites = new CopyOnWriteArrayList<>();
    private List<Sprite> newSprites = new CopyOnWriteArrayList<>();
    private List<Line2D.Double> lasers = new CopyOnWriteArrayList<>();
    private List<Line2D.Double> newLasers = new CopyOnWriteArrayList<>();

    private int level_origo_X = 0;
    private int level_origo_Y = 0;
    private int sprite_origo_x = 0;
    private int sprite_origo_y = 0 ;
    private int pad_origo_x = 0;
    private int pad_origo_y = 0 ;

    /**
     * Constructor of game screen panel that creates an instance of a game screen.
     */
    public GameScreenPanel() {

	//Creates Default things..
	createTroop("troop_v3");
	createLevel("defaultLevel");
	createTeleportPad();

	gameScreen = new JPanel() {
	    @Override
	    protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		drawLevel(g);
		drawSprites(g);
		drawLasers(g);
		drawTeleportPad(g,new Position(100,100));
	    }
	};

	gameScreen.setBackground(Color.BLACK);
    }

    /**
     * TODO: Idk why this is used.
     */
    public void refresh() {
	sprites.clear();
	lasers.clear();
	sprites.addAll(newSprites);
	lasers.addAll(newLasers);
	newSprites.clear();
	newLasers.clear();

	SwingUtilities.invokeLater(() -> {
	    gameScreen.repaint();
	});
    }

    /**
     * Returns the game screen as a JPanel to be used for a main frame.
     * @return GameScreen:JPanel, JPanel with the game screen.
     */
    public JPanel getJPanel(){
	return gameScreen;
    }

    /**
     * Draws a new troop by adding it to the list of sprites to be drawn.
     * @param x:int, position at x coordinate.
     * @param y:int, position at y coordinate.
     */
    public void drawTroop(double x, double y) {
	newSprites.add(new Sprite(troopImage, x, y));
    }

    /**
     * Draws a laser by adding it to the list of lasers to be drawn at every frame.
     * @param x1:int, starting x position.
     * @param y1:int, starting y position.
     * @param x2:int, target x position.
     * @param y2:int, target y position.
     */
    public void drawLaser(double x1, double y1, double x2, double y2) {
	newLasers.add(new Line2D.Double(x1, y1, x2, y2));
    }

    /**
     * Creates a new troop of the type given as argument string.
     * @param troopName:String, name of the troop type to be added.
     */
    private void createTroop(String troopName) {
	try {
	    troopImage = ImageIO.read(this.getClass().getResource("/images/troops/" + troopName + ".png"));
	} catch (Exception e) {
	    e.printStackTrace();
	    System.out.print("Troop creation Exception\n");
	}
    }

    /**
     * Creates a level of type given by the string argument.
     * @param levelName:String, name of the level to be created.
     */
    private void createLevel(String levelName) {
	try {
	    levelImage = ImageIO.read(this.getClass().getResource("/images/levels/"+levelName+".jpg"));
	} catch (Exception e) {
	    e.printStackTrace();
	    System.out.print("Level creation Exception\n");
	}
    }

    /**
     * Creates a teleport pad.
     */
    private void createTeleportPad() {
	try {
	    teleportPadImage = ImageIO.read(this.getClass().getResource("/images/levels/Portal.png"));
	} catch (Exception e) {
	    e.printStackTrace();
	    System.out.print("Pad creation Exception\n");
	}
    }

    /**
     * Draws the level by the paintComponent method
     * @param g:Graphics
     */
    private void drawLevel(Graphics g){
	Graphics2D g2d = (Graphics2D) g;

	//Makes rendering smooth
	RenderingHints rh = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
	rh.put(RenderingHints.KEY_RENDERING,RenderingHints.VALUE_RENDER_QUALITY);
	g2d.setRenderingHints(rh);

	g2d.drawImage(levelImage,0,0,1000,1000,null);
    }

    /**
     * Draws the sprites from the sprite list.
     * @param g:Graphics
     */
    private void drawSprites(Graphics g){
	Graphics2D g2d = (Graphics2D) g;

	//Makes rendering smooth
	RenderingHints rh = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
	rh.put(RenderingHints.KEY_RENDERING,RenderingHints.VALUE_RENDER_QUALITY);
	g2d.setRenderingHints(rh);

	// Draw sprites based on y position
	sprites.sort(Comparator.comparingDouble(Sprite::getY));
	for (Sprite sprite : sprites) {
	    sprite_origo_x = level_origo_X - (sprite.getImage().getWidth() / 2);
	    sprite_origo_y = level_origo_Y - (sprite.getImage().getHeight() / 2);

	    BufferedImage image = sprite.getImage();
	    g2d.drawImage(image,
		    sprite_origo_x + (int) sprite.getX(),
		    sprite_origo_y + (int) sprite.getY(), null);
	}
    }

    /**
     * Draws a laser with he given graphics.
     * @param g:Graphics.
     */
    private void drawLasers(Graphics g) {
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

    /**
     * Draws a teleporter pad at given position.
     * @param g:Graphics.
     * @param padPosition:Position, position of the teleporter pad.
     */
    private void drawTeleportPad(Graphics g, Position padPosition) {
	Graphics2D g2d = (Graphics2D) g;

	//Makes rendering smooth
	RenderingHints rh = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
	rh.put(RenderingHints.KEY_RENDERING,RenderingHints.VALUE_RENDER_QUALITY);
	g2d.setRenderingHints(rh);

	pad_origo_x = teleportPadImage.getWidth()/2;
	pad_origo_y = teleportPadImage.getHeight()/2;

	g2d.drawImage(teleportPadImage, (int)padPosition.getX()-pad_origo_x, (int)padPosition.getX()-pad_origo_x,null);
    }
}