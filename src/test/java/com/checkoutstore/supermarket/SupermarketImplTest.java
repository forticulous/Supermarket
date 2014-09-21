package com.checkoutstore.supermarket;

import com.checkoutstore.item.BulkDiscount;
import com.checkoutstore.item.Item;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

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

    @Test
    public void createTokenListTest() throws Exception {
        List<Character> expected = new ArrayList<>();
        expected.add('A');
        expected.add('B');
        expected.add('C');
        assertEquals(expected, impl.createTokenList("ABC"));
    }

    @Test
    public void trimAndCapitalizeTest() throws Exception {
        List<Character> expected = new ArrayList<>();
        expected.add('A');
        expected.add('B');
        expected.add('C');
        assertEquals(expected, impl.createTokenList("  abc  "));
    }

    @Test
    public void addItemPricesTest() throws Exception {
        Item it1 = new Item('A', 5, null);
        Item it2 = new Item('B', 6, null);
        Item it3 = new Item('C', 7, null);

        List<Item> items = new ArrayList<>();
        items.add(it1);
        items.add(it2);
        items.add(it3);

        assertEquals(18, impl.addItemPrices(items));
    }

    @Test
    public void applyBulkDiscountBelowQuantityTest() throws Exception {
        Item it1 = new Item('A', 1, new BulkDiscount(4, 4, 3));
        Item it2 = new Item('A', 1, new BulkDiscount(4, 4, 3));
        Item it3 = new Item('A', 1, new BulkDiscount(4, 4, 3));

        List<Item> items = new ArrayList<>();
        items.add(it1);
        items.add(it2);
        items.add(it3);

        List<Item> discountedItems = new ArrayList<>(items);

        // Should not apply discount because the quantity is not high enough
        assertEquals(0, impl.applyBulkDiscount(0, items, discountedItems));
    }

    @Test
    public void applyBulkDiscountMoreThanOnceTest() throws Exception {
        Item it1 = new Item('A', 1, new BulkDiscount(2, 2, 1));
        Item it2 = new Item('A', 1, new BulkDiscount(2, 2, 1));
        Item it3 = new Item('A', 1, new BulkDiscount(2, 2, 1));
        Item it4 = new Item('A', 1, new BulkDiscount(2, 2, 1));

        List<Item> items = new ArrayList<>();
        items.add(it1);
        items.add(it2);
        items.add(it3);
        items.add(it4);

        List<Item> discountedItems = new ArrayList<>(items);

        // discount should be applied twice
        assertEquals(-2, impl.applyBulkDiscount(0, items, discountedItems));
    }

}