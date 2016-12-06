/*
 * File: Payment.java
 * Author: Fredrik Johansson
 * Date: 2016-11-28
 */
package model.player;

/**
 * Holds an immutable currency which represent a payment
 */
public class Payment implements Comparable<Currency> {

    private final Currency currency;

    /**
     * Initiate Payment with a currency
     * @param currency Value to set as value
     */
    public Payment(Currency currency) {
        this.currency = currency;
    }

    /**
     * Compare Payment with Currency to determine if Payment is enough
     * @param c Currency to compare with
     * @return A negative integer, zero, or a positive integer as Payment
     *         is less than, equal to, or greater than the Currency.
     */
    @Override
    public int compareTo(Currency c) {
        return currency.getValue() - c.getValue();
    }
}
