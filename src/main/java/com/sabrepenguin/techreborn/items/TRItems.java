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
    public static final Item part = null;
    public static final Item plates = null;

    public static Item[] getItems() {
        final Item[] items = {
                new ItemMaterial("dust", "dust", new Dust()),
                new ItemMaterial("gem", "gem", new Gem(), "gem/", ""),
                new ItemMaterial("ingot", "ingot", new Ingot(), "ingot/", "_ingot"),
                new ItemMaterial("nuggets", "nuggets", new Nugget(), "nugget/", "_nugget"),
                new ItemMaterial("part", "part", new Part(), "part/", ""),
                new ItemMaterial("plates", "plate", new Plate(), "plate", ""),
                new ItemMaterial("smalldust", "dustsmall", new DustSmall()),
        };

        return items;
    }

    public static List<Item> getAllItems() {
        List<Item> allItems = new ArrayList<>();
        Collections.addAll(allItems,
                TRItems.dust,
                TRItems.gem,
                TRItems.ingot,
                TRItems.nuggets,
                TRItems.part,
                TRItems.plates,
                TRItems.smalldust
        );
        return allItems;
    }
}
