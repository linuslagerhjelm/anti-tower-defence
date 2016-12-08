package model.entities;

import exceptions.NoSuchPadException;

/**
 * Author: Linus Lagerhjelm
 * File: PadFactory
 * Created: 16-11-28
 * Description: Create different pads using the Factory method pattern
 */
public class PadFactory {

    private PadFactory() {
        // Do not allow construction of a factory
    }

    /**
     * Creates a concrete pad instance specified by the provided
     * @param type Type of pad to be instantiated
     * @return Concrete pad instance of type
     * @throws NoSuchPadException if the pad class could not be instantiated
     */
    public static Pad newInstance(String type) throws NoSuchPadException {

        Pad pad = null;
        try {
            pad = (Pad)Class.forName("model.entities."+type).newInstance();

        } catch (ClassNotFoundException e) {
            throw new NoSuchPadException("Couldn't find pad: " + type);
        } catch (IllegalAccessException e) {
            throw new NoSuchPadException("Not allowed to create pad: " + type);
        } catch (InstantiationException e) {
            throw new NoSuchPadException("Failed while trying to instantiate pad: " + type);
        }

        return pad;
    }
}
