package com.checkoutstore.item;

import java.util.HashMap;
import java.util.Map;

/**
 * ItemLookup retrieves pricing and bulk discount information,
 * and is responsible for creating Item instances.
 *
 * @author mgibson
 */
public class ItemLookup {

    private final Map<Character, Integer> prices = new HashMap<>();
    private final Map<Character, BulkDiscount> bulkDiscounts = new HashMap<>();

    public ItemLookup() {
        loadItemData();
    }

    /**
     * Load pricing and bulk discount information into memory.
     * This method could be replaced by a Database lookup.
     */
    private void loadItemData() {
        prices.put('A', 20);
        prices.put('B', 50);
        prices.put('C', 30);

        bulkDiscounts.put('B', new BulkDiscount(5, 5 * prices.get('B'), 3 * prices.get('B')));
    }

    /**
     * Create an {@link Item} object from a character identifier
     */
    public Item lookupItem(char c) {
        if (!prices.containsKey(c)) {
            throw new IllegalArgumentException("Unexpected item: " + c);
        }
        return new Item(c, prices.get(c), bulkDiscounts.get(c));
    }

}
