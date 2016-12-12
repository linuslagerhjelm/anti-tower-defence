package model.entities.troupe;

import model.entities.Node;
import model.level.Position;

/**
 * Author: Linus Lagerhjelm
 * File: DefaultTroupe
 * Created: 2016-12-07
 * Description: The Troupe that will be instantiated if no other
 * Troupe type were specified.
 */
class DefaultTroupe implements Troupe {
    @Override
    public void setStartNode(Node start) {

    }

    @Override
    public void setKilledListener(KilledListener listener) {

    }

    @Override
    public void setGoalListener(GoalListener listener) {

    }

    @Override
    public void receiveDamage(int damage) {

    }

    @Override
    public TroupeStats getStats() {
        return null;
    }

    @Override
    public double getLengthWalked() {
        return 0;
    }

    @Override
    public void update(double dt) {

    }

    @Override
    public void interact() {

    }

    @Override
    public void setPosition(Position position) {

    }

    @Override
    public Position getPosition() {
        return null;
    }
}
