package model.entities.tower;

import exceptions.NoSuchTowerException;

/**
 * Author: Linus Lagerhjelm
 * File: TowerFactory
 * Created: 16-11-28
 * Description: Factory to create tower based on their names
 */
public class TowerFactory {

    /**
     * Create tower based on name. Uses string to be able to be used with
     * for example XML.
     * @param type Name of tower. Should be the towers class name
     * @return A tower of specified type
     * @throws NoSuchTowerException If the type corresponds to no tower
     */
    public static Tower newInstance(String type) throws NoSuchTowerException {
        if (type.equals("SmallTower")) {
            return new SmallTower();
        } else if (type.equals("LargeTower")) {
            return new LargeTower();
        } else {
            throw new NoSuchTowerException();
        }
    }
}
