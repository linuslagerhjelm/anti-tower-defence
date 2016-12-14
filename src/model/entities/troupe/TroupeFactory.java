/*
 * File: TroupeFactory.java
 * Author: Fredrik Johansson
 * Date: 2016-11-28
 */
package model.entities.troupe;

import exceptions.NotEnoughFoundsException;
import model.player.Currency;
import model.player.Payment;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TroupeFactory {

    public final static Currency defaultTroupeCost = new Currency(5);
    public final static Currency teleportTroupeCost = new Currency(20);

    private final static Map<String, Currency> costs = new HashMap<>();

    static {
        costs.put("TeleportTroupe", teleportTroupeCost);
        costs.put("DefaultTroupe", defaultTroupeCost);
    }

    public static Currency getCost(String name) {
        return costs.get(name);
    }

    /**
     * Returns the cost for the cheapest troupe this factory can produce
     * @return cost of the cheapest troupe
     */
    public static Currency getCheapestCost() {
        Currency cheapest = new Currency(0);
        Collection<Currency> lcosts = costs.values();
        for (Currency c : lcosts) {
            if (c.getValue() < cheapest.getValue()) {
                cheapest = c;
            }
        }
        return cheapest;
    }


    /**
     * Buy a new Troupe of type specified by troupeType. If the payment is
     * insufficient, a NotEnoughFoundsException exception will be thrown
     * @param payment payment for the troupe
     * @param troupeType type of troupe to buy
     * @return A troupe instance if sufficient types
     * @throws NotEnoughFoundsException thrown if insufficient founds
     */
    public static Troupe buyTroupe(Payment payment, String troupeType)
            throws NotEnoughFoundsException {

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
     * @throws NotEnoughFoundsException thrown if payment were insufficient
     */
    private static Troupe buyTeleportTroupe(Payment payment)
            throws NotEnoughFoundsException {

        if (payment.compareTo(teleportTroupeCost) >= 0) {
            return new TeleportTroupe();
        } else {
            throw new NotEnoughFoundsException("Payment not enough, should be: " +
                    teleportTroupeCost.getValue());
        }
    }

    /**
     * Buys a new DefaultTroupe
     * @param payment payment to pay for the troupe
     * @return the troupe
     * @throws NotEnoughFoundsException thrown if payment were insufficient
     */
    private static Troupe buyDefaultTroupe(Payment payment)
            throws NotEnoughFoundsException {

        if (payment.compareTo(defaultTroupeCost) >= 0) {
            return new DefaultTroupe();
        } else {
            throw new NotEnoughFoundsException("Payment not enough, should be: " +
                    defaultTroupeCost.getValue());
        }
    }
}
