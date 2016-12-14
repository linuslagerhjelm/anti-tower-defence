/*
 * File: Wallet.java
 * Author: Fredrik Johansson
 * Date: 2016-11-28
 */
package model.player;

/**
 * Wallet holds an amount of currency to be used to make payments.
 */
public class Wallet implements Comparable<Currency> {

    private Currency value;

    /**
     * Initiate an empty wallet, currency = 0
     */
    public Wallet() {
        this.value = new Currency(0);
    }

    /**
     * Initiate wallet with the currency
     * @param value Currency to start with
     */
    public Wallet(Currency value) {
        this.value = new Currency(value.getValue());
    }

    /**
     * Add an amount of currency to wallet
     * @param addition Amount to add
     */
    public void add(Currency addition) {
        value.addValue(addition);
    }

    /**
     * Take currency out of wallet as a Payment
     * @param amount Amount to take from wallet
     * @return A Payment with the same mount as the Currency given
     */
    public Payment getPayment(Currency amount) {
        if (amount.getValue() > value.getValue()) {
            throw new IllegalArgumentException(
                    "Trying to get payment larger than wallet");
        }

        value.subtractValue(amount);
        return new Payment(amount);
    }

    public String getFormattedContent() {
        return value.getValue()+"$";
    }

    public String toString() {
        return super.toString()+"{"+value.getValue()+"$}";
    }

    @Override
    public int compareTo(Currency o) {
        return value.getValue() - o.getValue();
    }
}
