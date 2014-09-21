package com.checkoutstore;

import com.checkoutstore.item.ItemLookup;
import com.checkoutstore.supermarket.Supermarket;
import com.checkoutstore.supermarket.SupermarketImpl;

/**
 * @author mgibson
 */
public class Main {

    /** Entry point of the program */
    public static void main(String[] args) {
        if (args == null || args.length == 0) {
            throw new IllegalArgumentException("Expected one argument with list of items like 'ABC'");
        }
        String items = args[0];

        ItemLookup itemLookup = new ItemLookup();
        Supermarket supermarket = new SupermarketImpl(itemLookup);
        int totalCost = supermarket.checkout(items);

        System.out.println("Items as entered: " + items);
        System.out.println("Total cost for items: " + totalCost);
    }

}
