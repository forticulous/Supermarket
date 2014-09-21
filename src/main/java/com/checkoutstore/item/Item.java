package com.checkoutstore.item;

import java.util.Objects;

/**
 * An Item is a single purchase item with associated price and discount information
 *
 * @author mgibson
 */
public class Item {

    private char identifier;
    private final int price;
    private final BulkDiscount bulkDiscount;

    public Item(char identifier, int price, BulkDiscount bulkDiscount) {
        this.identifier = identifier;
        this.price = price;
        this.bulkDiscount = bulkDiscount;
    }

    public char getIdentifier() {
        return identifier;
    }

    public int getPrice() {
        return price;
    }

    public BulkDiscount getBulkDiscount() {
        return bulkDiscount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Item that = (Item) o;

        return this.identifier == that.getIdentifier();
    }

    @Override
    public int hashCode() {
        return Objects.hash(identifier);
    }

}
