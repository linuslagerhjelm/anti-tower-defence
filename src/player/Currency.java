/*
 * File: Concurrency.java
 * Author: Fredrik Johansson
 * Date: 2016-11-28
 */
package player;


/**
 * Holds a positive value to represent a corrency
 */
public class Currency {

    private int value;

    /**
     * Initiates a currency with given value
     * @param value Value to hold
     */
    public Currency(int value) {
        if (value < 0) {
            throw new IllegalArgumentException("Currency can't be negative");
        }
        this.value = value;
    }

    /**
     * Returns value
     * @return value
     */
    public int getValue() {
        return value;
    }

    /**
     * Adds a value to current one. Guarantied to get larger
     * @param addition A positive currency
     */
    public void addValue(Currency addition) {
        value += addition.getValue();
    }

    /**
     * Subtracts a value from current one. Guarantied to get smaller
     * @param subtraction
     */
    public void subtractValue(Currency subtraction) {
        if (subtraction.getValue() > value) {
            throw new IllegalArgumentException("Subtracting to large value");
        }
        value -= subtraction.getValue();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Currency currency = (Currency) o;

        return value == currency.value;

    }

    @Override
    public int hashCode() {
        return value;
    }
}
