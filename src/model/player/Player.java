/*
 * File: Player.java
 * Author: Fredrik Johansson
 * Date: 2016-11-28
 */
package model.player;

import exceptions.NotEnoughFoundsException;
import model.entities.troupe.Troupe;
import model.entities.troupe.TroupeFactory;


public class Player {

    private Wallet wallet = new Wallet(new Currency(20000));

    public Troupe createTroupe(String troupeType) throws NotEnoughFoundsException {
        Payment payment;
        try {
            payment = wallet.getPayment(TroupeFactory.getCost(troupeType));

        } catch (IllegalArgumentException e) {
            throw new NotEnoughFoundsException("Not enough founds");
        }

        return TroupeFactory.buyTroupe(payment, troupeType);
    }

    public void addCurrency(Currency addition) {
        wallet.add(addition);
    }

    public String getCurrency() {
        return wallet.getFormattedContent();
    }

    public boolean canAfford(Currency c) {
        return wallet.compareTo(c) >= 0;
    }
}
