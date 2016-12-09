package model.entities.tower;

import exceptions.NoSuchTowerException;

/**
 * Author: Linus Lagerhjelm
 * File: TowerFactory
 * Created: 16-11-28
 * Description:
 */
public class TowerFactory {

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
