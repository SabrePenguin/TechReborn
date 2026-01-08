package com.sabrepenguin.techreborn.items;

import com.sabrepenguin.techreborn.Tags;
import com.sabrepenguin.techreborn.items.materials.*;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.registry.GameRegistry;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@GameRegistry.ObjectHolder(Tags.MODID)
public class TRItems {

    public static final Item ingot = null;
    public static final Item dust = null;
    public static final Item smalldust = null;
    public static final Item nuggets = null;
    public static final Item gem = null;

    public static Item[] getItems() {
        final Item[] items = {
                new ItemIngot(),
                new ItemDust(),
                new ItemDustSmall(),
                new ItemNugget(),
                new ItemGem(),
        };

        return items;
    }

    public static List<Item> getAllItems() {
        List<Item> allItems = new ArrayList<>();
        Collections.addAll(allItems, TRItems.ingot, TRItems.dust, TRItems.smalldust, TRItems.nuggets, TRItems.gem);
        return allItems;
    }
}
