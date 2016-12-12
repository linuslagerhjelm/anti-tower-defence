/*
 * File: Player.java
 * Author: Fredrik Johansson
 * Date: 2016-11-28
 */
package model.player;

public class Player {

    private Wallet wallet = new Wallet();

    public void addCurrency(Currency addition) {
        wallet.add(addition);
    }

    public String getCurrency() {
        return wallet.getContent();
    }
}
