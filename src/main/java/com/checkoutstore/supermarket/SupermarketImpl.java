package com.checkoutstore.supermarket;

import com.checkoutstore.item.BulkDiscount;
import com.checkoutstore.item.Item;
import com.checkoutstore.item.ItemLookup;

import java.util.List;
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

        // Can stop here if there are no items with bulk discount
        boolean noBulkDiscounts = items.stream()
                .allMatch(item -> item.getBulkDiscount() == null);
        if (noBulkDiscounts) {
            return currentTotal;
        }

        currentTotal = applyBulkDiscount(currentTotal, items);

        return currentTotal;
    }

    /** Reduces total by applying discounts for bulk purchase */
    int applyBulkDiscount(int currentTotal, List<Item> items) {
        List<Item> distinctBulkDiscountItems = items.stream()
                .filter(item -> item.getBulkDiscount() != null)
                .distinct()
                .collect(Collectors.toList());

        for (Item discountItem : distinctBulkDiscountItems) {
            BulkDiscount bulkDiscount = discountItem.getBulkDiscount();

            long itemCount = items.stream().filter(item -> item.equals(discountItem)).count();
            long timesDiscounted = Math.floorDiv(itemCount, bulkDiscount.getQuantity());
            currentTotal -= timesDiscounted * bulkDiscount.getNormalPrice();
            currentTotal += timesDiscounted * bulkDiscount.getDiscountPrice();
        }
        return currentTotal;
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
