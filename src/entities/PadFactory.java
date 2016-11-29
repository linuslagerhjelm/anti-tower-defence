package entities;

/**
 * Author: Linus Lagerhjelm
 * File: PadFactory
 * Created: 16-11-28
 * Description:
 */
public class PadFactory {

    public static Pad newInstance(String type) {
        return new TeleportPad();
    }
}
