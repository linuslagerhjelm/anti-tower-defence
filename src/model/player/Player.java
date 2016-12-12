/*
 * File: Player.java
 * Author: Fredrik Johansson
 * Date: 2016-11-28
 */
package model.player;

import exceptions.NotEnoughFounds;
import model.entities.troupe.Troupe;
import model.entities.troupe.TroupeFactory;


public class Player {

    private Wallet wallet = new Wallet(new Currency(100000));

    public Troupe createTroupe(String troupeType) throws NotEnoughFounds {
        Payment payment;
        try {
            payment = wallet.getPayment(TroupeFactory.getCost(troupeType));

        } catch (IllegalArgumentException e) {
            throw new NotEnoughFounds("Not enough founds");
        }

        return TroupeFactory.buyTroupe(payment, troupeType);
    }

    public void addCurrency(Currency addition) {
        wallet.add(addition);
    }

    public String getCurrency() {
        return wallet.getContent();
    }
}
