package model.entities;

import model.level.Position;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Author: Linus Lagerhjelm
 * File: TeleportPadTest
 * Created: 2016-12-08
 * Description: Test cases for the TeleportPad class
 */
public class TeleportPadTest {
    private static final int X = 1;
    private static final int Y = 1;
    private static final int HEIGHT = 10;
    private static final int WIDTH = 10;
    private static TeleportPad pad;

    @BeforeClass
    public static void setup() {
        pad = new TeleportPad();
        pad.setProperties(X, Y, WIDTH, HEIGHT);
    }

    @Test
    public void testGetPosition() throws Exception {
        assertEquals(new Position((double)X, (double)Y), pad.getPosition());
    }

    @Test
    public void testGetHeight() throws Exception {
        assertEquals(pad.getHeight(), HEIGHT);
    }

    @Test
    public void testGetWidth() throws Exception {
        assertEquals(pad.getWidth(), WIDTH);
    }

}