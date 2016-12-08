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

public abstract class Tower implements Entity {

    public interface ShootListener {
        void onShoot(Position from, Position to);
    }

    private Position position;
    private Timer shootCountDown = new Timer("TowerCountdownThread");
    private AtomicBoolean canShoot = new AtomicBoolean(true);
    private ShootListener listener;


    public abstract void inRange(Troupe troupe);

    public abstract TowerStats getStats();

    public void addShootListener(ShootListener listener) {
        this.listener = listener;
    }

    protected boolean canShoot() {
        return canShoot.get();
    }

    protected void haveShoot(long delay, Troupe troupe) {
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

    @Override
    public void interact() {}

    @Override
    public void setPosition(Position position) {
        this.position = position;
    }

    @Override
    public Position getPosition() {
        return position;
    }
}
