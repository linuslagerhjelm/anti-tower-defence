package view;

import java.awt.image.BufferedImage;

/**
 * Author: Andreas, Arvid.
 * File: Sprite.java
 * Created: 2016-12-05
 * Description: Sprite with a position and an buffer image.
 */
public class Sprite {

    private BufferedImage spriteImage = null;
    private double x;
    private double y;

    public Sprite(BufferedImage bI, double x, double y){
        spriteImage = bI;
        this.x = x;
        this.y = y;
    }

    /**
     * Gets the x position of the sprite.
     * @return x:int, x position of the sprite.
     */
    public double getX() {
        return x;
    }

    /**
     * Gets the y position of the sprite.
     * @return y:int, y position of the sprite.
     */
    public double getY() {
        return y;
    }

    /**
     * Gets the Image being used of the sprite.
     * @return spriteImage:BufferedImage, image of the sprite.
     */
    public BufferedImage getImage(){
        return spriteImage;
    }
}
