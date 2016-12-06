package model.entities;

/**
 * Author: Linus Lagerhjelm
 * File: PadFactory
 * Created: 16-11-28
 * Description: Create different pads using the Factory method pattern
 */
public class PadFactory {

    /**
     * Creates a concrete pad instance specified by the provided
     * @param type Type of pad to be instantiated
     * @return Concrete pad instance of type
     */
    public static Pad newInstance(String type) {
        if (type.equalsIgnoreCase("TeleportPad")) {
            return new TeleportPad();
        } else {
            return new TeleportPad();
        }
    }
}
