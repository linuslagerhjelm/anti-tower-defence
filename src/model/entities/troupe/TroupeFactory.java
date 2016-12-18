/*
 * File: TroupeFactory.java
 * Author: Fredrik Johansson
 * Date: 2016-11-28
 */
package model.entities.troupe;

import exceptions.NotEnoughFoundsException;
import model.player.Currency;
import model.player.Payment;

import java.util.*;

/**
 * A factory to create troupes based on troupe title and cost
 */
public class TroupeFactory {

    public final static Currency defaultTroupeCost =
                new Currency(DefaultTroupe.STATS.getCost());
    public final static Currency teleportTroupeCost =
                new Currency(TeleportTroupe.STATS.getCost());

    private final static Map<String, Currency> costs = new HashMap<>();

    static {
        costs.put(TeleportTroupe.STATS.getTitle(), teleportTroupeCost);
        costs.put(DefaultTroupe.STATS.getTitle(), defaultTroupeCost);
    }

    /**
     * Get costs for a troupe based on name
     * @param name Title of a troupe
     * @return Cost for troupe in form of currency. Null if name doesn't
     *         corresponds to a troupe
     */
    public static Currency getCost(String name) {
        return costs.get(name);
    }

    /**
     * Returns the cost for the cheapest troupe this factory can produce
     * @return cost of the cheapest troupe
     */
    public static Currency getCheapestCost() {
        Collection<Currency> lcosts = costs.values();
        Currency cheapest = lcosts.iterator().next(); // first cost
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

        if (troupeType.equalsIgnoreCase(TeleportTroupe.STATS.getTitle())) {
            return buyTeleportTroupe(payment);

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

    /**
     * Returns a list of stats of all the available troupes.
     * @return list of troupe stats
     */
    public static List<TroupeStats> getTroupeStats() {
        List<TroupeStats> troupes = new ArrayList<>();

        troupes.add(DefaultTroupe.STATS);
        troupes.add(TeleportTroupe.STATS);

        return troupes;
    }
}
