/*
 * File: ConcurrencyTest.java
 * Author: Fredrik Johansson
 * Date: 2016-11-28
 */

package model.player;

import org.junit.Test;

import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class CurrencyTest {

    @Test
    public void shouldHoldInteger3() {
        Currency c = new Currency(3);
        assertEquals(3, c.getValue());
    }

    @Test
    public void shouldHoldInteger8() {
        Currency c = new Currency(8);
        assertEquals(8, c.getValue());
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldNotAllowNegative() {
        Currency c = new Currency(-2);
    }

    @Test
    public void shouldCompareSameInteger() {
        Currency c1 = new Currency(1);
        Currency c2 = new Currency(1);
        assertEquals(c1, c2);
    }

    @Test
    public void shouldCompareDifferentInteger() {
        Currency c1 = new Currency(1);
        Currency c2 = new Currency(2);
        assertNotEquals(c1, c2);
    }

    @Test
    public void shouldAddValue() {
        Currency c = new Currency(1);
        c.addValue(new Currency(2));
        assertEquals(new Currency(3), c);
    }

    @Test
    public void shouldSubtractValue() {
        Currency c = new Currency(3);
        c.subtractValue(new Currency(2));
        assertEquals(new Currency(1), c);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldNotAllowToSubtractToNegative() {
        Currency c = new Currency(3);
        c.subtractValue(new Currency(4));
    }

    @Test
    public void shouldHaveEqualHash() {
        Currency c1 = new Currency(1);
        Currency c2 = new Currency(1);
        assertEquals(c1.hashCode(), c2.hashCode());
    }

    @Test
    public void shouldHaveUnequalHash() {
        Currency c1 = new Currency(1);
        Currency c2 = new Currency(2);
        assertNotEquals(c1.hashCode(), c2.hashCode());
    }
}