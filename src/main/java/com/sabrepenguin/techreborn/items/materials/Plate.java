package com.sabrepenguin.techreborn.items.materials;

import com.sabrepenguin.techreborn.items.MetadataItem;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class Plate implements IMaterial {

    private static final List<MetadataItem> ORDERED_ITEMS = new ArrayList<>();
    private static final Int2ObjectMap<MetadataItem> META = new Int2ObjectOpenHashMap<>();
    // I'll switch later if Sai is fine without metadata
    static {
        ORDERED_ITEMS.addAll(
                Arrays.asList(
                        new MetadataItem(0, "iron"),
                        new MetadataItem(1, "gold"),
                        new MetadataItem(2, "carbon"),
                        new MetadataItem(3, "wood"),
                        new MetadataItem(4, "redstone"),
                        new MetadataItem(5, "diamond"),
                        new MetadataItem(6, "emerald"),
//                        new MetadataItem(7, "none"),
                        new MetadataItem(8, "coal"),
                        new MetadataItem(9, "obsidian"),
                        new MetadataItem(10, "lazurite"),
                        new MetadataItem(11, "silicon"),
                        // Gems
                        new MetadataItem(12, "ruby"),
                        new MetadataItem(13, "sapphire"),
                        new MetadataItem(14, "peridot"),
                        new MetadataItem(15, "red_garnet"),
                        new MetadataItem(16, "yellow_garnet"),
                        // Ingots
                        new MetadataItem(17, "aluminum"),
                        new MetadataItem(18, "brass"),
                        new MetadataItem(19, "bronze"),
                        new MetadataItem(20, "chrome"),
                        new MetadataItem(21, "copper"),
                        new MetadataItem(22, "electrum"),
                        new MetadataItem(23, "invar"),
                        new MetadataItem(24, "iridium"),
                        new MetadataItem(25, "lead"),
                        new MetadataItem(26, "nickel"),
                        new MetadataItem(27, "platinum"),
                        new MetadataItem(28, "silver"),
                        new MetadataItem(29, "steel"),
                        new MetadataItem(30, "tin"),
                        new MetadataItem(31, "titanium"),
                        new MetadataItem(32, "tungsten"),
                        new MetadataItem(33, "tungstensteel"),
                        new MetadataItem(34, "zinc"),
                        new MetadataItem(35, "refined_iron"),
                        new MetadataItem(36, "advanced_alloy"),
                        new MetadataItem(37, "magnalium"),
                        new MetadataItem(38, "iridium_alloy")
                )
        );
        // Verbotten
        //             "hot", // Hot ingots
        //            "mixed_metal", // Mixed metal has own version of plate
        //            "iridium_alloy", // Iridium alloy is plate itself
        //            "thorium",
        //            "uranium",
        //            "plutonium",
        //            MetadataHelper.META_PLACEHOLDER // ...
        for (MetadataItem item: ORDERED_ITEMS) {
            META.put(item.meta(), item);
        }
    }


    public static void addSmallDust(String name, int metadata) {
        if (!META.containsKey(metadata))
            META.put(metadata, new MetadataItem(metadata, name));
    }

    @Override
    public String getNameFromMeta(int meta) {
        if (META.containsKey(meta)) {
            return META.get(meta).name();
        }
        return "";
    }

    @Override
    public Collection<MetadataItem> getItems() {
        return META.values();
    }

    @Override
    public int getMetaFromName(String name) {
        for(MetadataItem item: META.values()) {
            if (item.name().equals(name))
                return item.meta();
        }
        return 0;
    }

    @Override
    public List<MetadataItem> getOrderedItems() {
        return ORDERED_ITEMS;
    }

    @Override
    public String getOreDict() {
        return "plate";
    }
}
