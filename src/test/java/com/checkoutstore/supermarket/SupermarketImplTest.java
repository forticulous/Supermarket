package com.checkoutstore.supermarket;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class SupermarketImplTest {

    private SupermarketImpl impl;

    @Before
    public void setUp() throws Exception {
        impl = new SupermarketImpl(null);
    }

    @Test
    public void calcTimesDivisibleTest() throws Exception {
        assertEquals(0L, impl.calcTimesDivisible(2, 4));
        assertEquals(1L, impl.calcTimesDivisible(4, 4));
        assertEquals(2L, impl.calcTimesDivisible(11, 5));
    }

}