/*
 * File: Player.java
 * Author: Fredrik Johansson
 * Date: 2016-11-28
 */
package model.player;

import exceptions.NotEnoughFoundsException;
import model.entities.troupe.Troupe;
import model.entities.troupe.TroupeFactory;

/**
 * Represent a player which hold the money.
 */
public class Player {

    private Wallet wallet = new Wallet(new Currency(1000));

    /**
     * Create a troupe using the money which the user have
     * @param troupeType Title of troupe to create
     * @return Troupe which was bought
     * @throws NotEnoughFoundsException Not enough money to buy the troupe
     */
    public Troupe createTroupe(String troupeType) throws NotEnoughFoundsException {
        Payment payment;
        try {
            payment = wallet.getPayment(TroupeFactory.getCost(troupeType));

        } catch (IllegalArgumentException e) {
            throw new NotEnoughFoundsException("Not enough founds");
        }

        return TroupeFactory.buyTroupe(payment, troupeType);
    }

    /**
     * Add currency to the player
     * @param addition Addition to add
     */
    public void addCurrency(Currency addition) {
        wallet.add(addition);
    }

    /**
     * Get current amount of money
     * @return Current amount of money in form of a String
     */
    public String getCurrency() {
        return wallet.getFormattedContent();
    }

    /**
     * If player can afford something of this value (Currency)
     * @param c Amount to compare to
     * @return If player can afford or not
     */
    public boolean canAfford(Currency c) {
        return wallet.compareTo(c) >= 0;
    }
}
