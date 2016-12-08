/*
 * File: TroupeFactory.java
 * Author: Fredrik Johansson
 * Date: 2016-11-28
 */
package model.entities;

import exceptions.NotEnoughFounds;
import model.player.Currency;
import model.player.Payment;

public class TroupeFactory {

    public final static Currency defaultTroupeCost = new Currency(5);
    public final static Currency teleportTroupeCost = new Currency(20);

    /**
     * Buy a new Troupe of type specified by troupeType. If the payment is
     * insufficient, a NotEnoughFounds exception will be thrown
     * @param payment payment for the troupe
     * @param troupeType type of troupe to buy
     * @return A troupe instance if sufficient types
     * @throws NotEnoughFounds thrown if insufficient founds
     */
    public static Troupe buyTroupe(Payment payment, String troupeType)
            throws NotEnoughFounds{

        if (troupeType.equalsIgnoreCase("TeleportTroupe")) {
            return buyDefaultTroupe(payment);

        } else {
            return buyDefaultTroupe(payment);
        }
    }

    /**
     * Buys a new TeleportTroupe
     * @param payment payment to pay for the troupe
     * @return the troupe
     * @throws NotEnoughFounds thrown if payment were insufficient
     */
    private static Troupe buyTeleportTroupe(Payment payment)
            throws NotEnoughFounds {

        if (payment.compareTo(teleportTroupeCost) < 0) {
            return new TeleportTroupe();
        } else {
            throw new NotEnoughFounds("Payment not enough, should be: " +
                    teleportTroupeCost.getValue());
        }
    }

    /**
     * Buys a new DefaultTroupe
     * @param payment payment to pay for the troupe
     * @return the troupe
     * @throws NotEnoughFounds thrown if payment were insufficient
     */
    private static Troupe buyDefaultTroupe(Payment payment)
            throws NotEnoughFounds {

        if (payment.compareTo(defaultTroupeCost) < 0) {
            return new DefaultTroupe();
        } else {
            throw new NotEnoughFounds("Payment not enough, should be: " +
                    defaultTroupeCost.getValue());
        }
    }
}
