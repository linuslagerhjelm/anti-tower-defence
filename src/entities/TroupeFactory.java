/*
 * File: TroupeFactory.java
 * Author: Fredrik Johansson
 * Date: 2016-11-28
 */
package entities;

import exceptions.NotEnoughFounds;
import player.Currency;
import player.Payment;

public class TroupeFactory {

    public final static Currency teleportTroupeCost = new Currency(20);

    public static Troupe buyTeleportTroupe(Payment payment)
                                                    throws NotEnoughFounds {

        if (payment.compareTo(teleportTroupeCost) < 0) {
            return new TeleportTroupe();
        } else {
            throw new NotEnoughFounds("Payment not enough, should be: " +
                    teleportTroupeCost.getValue());
        }
    }
}
