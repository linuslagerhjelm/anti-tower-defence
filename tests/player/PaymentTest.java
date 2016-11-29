/*
 * File: PaymentTest.java
 * Author: Fredrik Johansson
 * Date: 2016-11-28
 */

package player;

import org.junit.Test;

import static junit.framework.TestCase.assertEquals;

public class PaymentTest {

    @Test
    public void shouldTakeCurrency() {
        new Payment(new Currency(3));
    }

    @Test
    public void shouldCompareEqualCurrency() {
        Payment p = new Payment(new Currency(4));
        assertEquals(0, p.compareTo(new Currency(4)));
    }

    @Test
    public void shouldCompareLargerCurrency() {
        Payment p = new Payment(new Currency(5));
        assertEquals(-1, p.compareTo(new Currency(6)));
    }

    @Test
    public void shouldCompareSmallerCurrency() {
        Payment p = new Payment(new Currency(6));
        assertEquals(1, p.compareTo(new Currency(5)));
    }
}