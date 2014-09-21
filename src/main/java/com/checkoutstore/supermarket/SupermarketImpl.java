package com.checkoutstore.supermarket;

import com.checkoutstore.item.BulkDiscount;
import com.checkoutstore.item.Item;
import com.checkoutstore.item.ItemLookup;

import java.util.ArrayList;
import java.util.Arrays;
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
        long timesDivisible = 0;
        while (numerator >= denominator) {
            numerator -= denominator;
            timesDivisible++;
        }
        return timesDivisible;
    }

    /** Get total price by adding all item prices */
    private int addItemPrices(List<Item> items) {
        int currentTotal = 0;
        for (Item item : items) {
            currentTotal += item.getPrice();
        }
        return currentTotal;
    }

    /** Convert String into List of Characters */
    private List<Character> createTokenList(String source) {
        char[] chars = source.trim().toUpperCase().toCharArray();
        Character[] boxChars = asBoxedArray(chars);
        List<Character> characters = new ArrayList<>(Arrays.asList(boxChars));
        return characters;
    }

    private Character[] asBoxedArray(char[] unboxed) {
        Character[] boxed = new Character[unboxed.length];
        for (int i = 0; i < unboxed.length; i++) {
            boxed[i] = unboxed[i];
        }
        return boxed;
    }

}