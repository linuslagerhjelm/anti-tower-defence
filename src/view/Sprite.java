package view;

import java.awt.image.BufferedImage;

/**
 * Created by c15awl on 2016-12-05.
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

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public BufferedImage getImage(){

        return spriteImage;
    }
}
