package com.checkoutstore.supermarket;

import com.checkoutstore.item.BulkDiscount;
import com.checkoutstore.item.Item;
import com.checkoutstore.item.ItemLookup;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Supermarket implementation
 *
 * @author mgibson
 */
public class SupermarketImpl implements Supermarket {

    private final ItemLookup itemLookup;

    public SupermarketImpl(ItemLookup itemLookup) {
        this.itemLookup = itemLookup;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int checkout(String source) {
        if (source == null || source.length() == 0) {
            return 0;
        }
        List<Character> tokens = createTokenList(source);
        List<Item> items = tokens.stream()
                .map(itemLookup::lookupItem)
                .collect(Collectors.toList());

        int currentTotal = addItemPrices(items);

        List<Item> itemsWithDiscount = items.stream()
                .filter(item -> item.getBulkDiscount() != null)
                .collect(Collectors.toList());

        // Can stop here if there are no items with bulk discount
        if (itemsWithDiscount.size() == 0) {
            return currentTotal;
        }

        currentTotal = applyBulkDiscount(currentTotal, items, itemsWithDiscount);

        return currentTotal;
    }

    /** Reduces total by applying discounts for bulk purchase */
    int applyBulkDiscount(int currentTotal, List<Item> items, List<Item> itemsWithDiscount) {
        Map<Item, BulkDiscount> discounts = new HashMap<>();
        for (Item item : itemsWithDiscount) {
            discounts.put(item, item.getBulkDiscount());
        }
        for (Map.Entry<Item, BulkDiscount> entry : discounts.entrySet()) {
            Item discountItem = entry.getKey();
            BulkDiscount bulkDiscount = entry.getValue();

            long itemCount = items.stream().filter(item -> item.equals(discountItem)).count();
            long timesDiscounted = calcTimesDivisible(itemCount, bulkDiscount.getQuantity());
            currentTotal -= timesDiscounted * bulkDiscount.getNormalPrice();
            currentTotal += timesDiscounted * bulkDiscount.getDiscountPrice();
        }
        return currentTotal;
    }

    /** Calculate the number of times a number is divisible by another */
    long calcTimesDivisible(long numerator, long denominator) {
        long timesDivisible = Math.floorDiv(numerator, denominator);
        return timesDivisible;
    }

    /** Get total price by adding all item prices */
    int addItemPrices(List<Item> items) {
        int currentTotal = items.stream()
                .mapToInt(Item::getPrice)
                .sum();
        return currentTotal;
    }

    /** Convert String into List of Characters */
    List<Character> createTokenList(String source) {
        String sanitized = source.trim().toUpperCase();
        List<Character> charList = sanitized.chars()
                .mapToObj(i -> (char) i)
                .collect(Collectors.toList());
        return charList;
    }

}
