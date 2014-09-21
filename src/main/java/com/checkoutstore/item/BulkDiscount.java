package com.checkoutstore.item;

/**
 * BulkDiscount holds information about bulk purchase discounts
 *
 * @author mgibson
 */
public class BulkDiscount {

    private final int quantity;
    private final int normalPrice;
    private final int discountPrice;

    public BulkDiscount(int quantity, int normalPrice, int discountPrice) {
        this.quantity = quantity;
        this.normalPrice = normalPrice;
        this.discountPrice = discountPrice;
    }

    public int getQuantity() {
        return quantity;
    }

    public int getNormalPrice() {
        return normalPrice;
    }

    public int getDiscountPrice() {
        return discountPrice;
    }
}
