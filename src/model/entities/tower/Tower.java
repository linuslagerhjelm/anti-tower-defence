/*
 * File: Tower.java
 * Author: Fredrik Johansson
 * Date: 2016-11-28
 */
package model.entities.tower;


import model.entities.Entity;
import model.entities.troupe.Troupe;
import model.level.Position;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Abstract implementation of tower. This includes the basic of tower
 * logic, such as handle if and when a tower shoots.
 */
public abstract class Tower implements Entity {

    /**
     * ShootListener is a listener which is triggered whenever this
     * tower shoots, with to <i>from</i> and <i>to</i> position.
     */
    public interface ShootListener {
        void onShoot(Position from, Position to);
    }

    private Position position;
    private Timer shootCountDown = new Timer("TowerCountdownThread");
    private AtomicBoolean canShoot = new AtomicBoolean(true);
    private ShootListener listener;


    /**
     * Asks if troupe is in range of tower
     * @param troupe The troupe to check position of
     */
    public abstract void inRange(Troupe troupe);

    /**
     * Return the stats (range, cooldown, damage, etc) of the tower
     * @return A TowerStats object with included information
     */
    public abstract TowerStats getStats();

    /**
     * Sets the ShootListener for the tower. Only one listener can be set
     * to a tower
     * @param listener Listener for shots
     */
    public void setShootListener(ShootListener listener) {
        this.listener = listener;
    }

    /**
     * Asks if tower can shoot based on cooldown. Should be used by subclasses
     * @return If tower is allowed to shoot
     */
    protected boolean canShoot() {
        return canShoot.get();
    }

    /**
     * Sets the tower to just have fired. Should be used by subclasses.
     * @param delay Cooldown for when next shot is allowed
     * @param troupe The troupe which have been shot
     */
    protected void haveShot(long delay, Troupe troupe) {
        canShoot.set(false);

        shootCountDown.schedule(new TimerTask() {
            @Override
            public void run() {
                canShoot.set(true);
            }
        }, delay);

        if (listener != null) {
            listener.onShoot(position, troupe.getPosition());
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void interact() {}

    /**
     * {@inheritDoc}
     */
    @Override
    public void setPosition(Position position) {
        this.position = position;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Position getPosition() {
        return position;
    }
}
