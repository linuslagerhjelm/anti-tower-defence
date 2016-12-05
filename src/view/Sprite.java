package view;

import level.Position;

import java.awt.image.BufferedImage;

/**
 * Created by c15awl on 2016-12-05.
 */
public class Sprite {

    private BufferedImage spriteImage = null;
    private Position pos = null;

    public Sprite(BufferedImage bI, Position p){
        pos = p;
        spriteImage = bI;
    }

    public Position getPos(){

        return pos;
    }

    public BufferedImage getImage(){

        return spriteImage;
    }
}
