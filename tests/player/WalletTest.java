/*
 * File: WalletTest.java
 * Author: Fredrik Johansson
 * Date: 2016-11-28
 */

package player;

import org.junit.Test;

import static junit.framework.TestCase.assertEquals;

public class WalletTest {

    @Test
    public void shouldHandleEmptyConstructor() {
        new Wallet();
    }

    @Test
    public void shouldTakeCurrency() {
        new Wallet(new Currency(2));
    }

    @Test
    public void shouldAddCurrency() {
        new Wallet().add(new Currency(3));
    }

    @Test
    public void shouldCompareEquals() {
        Wallet w = new Wallet(new Currency(3));
        assertEquals(0, w.compareTo(new Currency(3)));
    }

    @Test
    public void shouldCompareLarger() {
        Wallet w = new Wallet(new Currency(4));
        assertEquals(1, w.compareTo(new Currency(3)));
    }

    @Test
    public void shouldCompareSmaller() {
        Wallet w = new Wallet(new Currency(5));
        assertEquals(-1, w.compareTo(new Currency(6)));
    }

    @Test
    public void shouldCompareEmptyWalletAsZero() {
        Wallet w = new Wallet();
        assertEquals(0, w.compareTo(new Currency(0)));
    }

    @Test
    public void shouldReduceWithPayment() {
        Wallet w = new Wallet(new Currency(3));
        w.getPayment(new Currency(2));
        assertEquals(0, w.compareTo(new Currency(1)));
    }

    @Test
    public void shouldReturnCorrectPayment() {
        Wallet w = new Wallet(new Currency(3));
        Payment p = w.getPayment(new Currency(2));
        assertEquals(0, p.compareTo(new Currency(2)));
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldNotAllowLargerPaymentsThanWallet() {
        Wallet w = new Wallet(new Currency(3));
        w.getPayment(new Currency(4));
    }
 }