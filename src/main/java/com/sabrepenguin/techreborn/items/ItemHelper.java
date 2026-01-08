package com.sabrepenguin.techreborn.items;

import net.minecraft.item.Item;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ItemHelper {
    public static final String META_PLACEHOLDER = "PLACEHOLDER_ITEM";

    private static final List<Item> allItems = new ArrayList<>();

    static {
        Collections.addAll(allItems, TRItems.ingot, TRItems.dust, TRItems.smalldust, TRItems.nuggets);
    }

    public static List<Item> getAllItems() {
        return allItems;
    }
}
