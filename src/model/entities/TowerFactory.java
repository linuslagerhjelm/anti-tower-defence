package model.entities;

/**
 * Author: Linus Lagerhjelm
 * File: TowerFactory
 * Created: 16-11-28
 * Description:
 */
public class TowerFactory {

    public static Tower newInstance(String type) {
        return new SmallTower();
    }
}
