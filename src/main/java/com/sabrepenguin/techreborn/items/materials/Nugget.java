package com.sabrepenguin.techreborn.items.materials;

import com.sabrepenguin.techreborn.items.MetadataItem;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class Nugget implements IMaterial {

    private static final List<MetadataItem> ORDERED_ITEMS = new ArrayList<>();
    private static final Int2ObjectMap<MetadataItem> META = new Int2ObjectOpenHashMap<>();
    // I'll switch later if Sai is fine without metadata
    static {
        ORDERED_ITEMS.addAll(
                Arrays.asList(
                        new MetadataItem(0, "aluminum"),
                        new MetadataItem(1, "brass"),
                        new MetadataItem(2, "bronze"),
                        new MetadataItem(3, "chrome"),
                        new MetadataItem(4, "copper"),
                        new MetadataItem(5, "electrum"),
                        new MetadataItem(6, "invar"),
                        new MetadataItem(7, "iridium"),
                        new MetadataItem(8, "lead"),
                        new MetadataItem(9, "nickel"),
                        new MetadataItem(10, "platinum"),
                        new MetadataItem(11, "silver"),
                        new MetadataItem(12, "steel"),
                        new MetadataItem(13, "tin"),
                        new MetadataItem(14, "titanium"),
                        new MetadataItem(15, "tungsten"),
                        new MetadataItem(16, "hot_tungstensteel"),
                        new MetadataItem(17, "tungstensteel"),
                        new MetadataItem(18, "zinc"),
                        new MetadataItem(19, "refined_iron"),
//                        new MetadataItem(20, "advanced_alloy"),
//                        new MetadataItem(21, "mixed_metal"),
//                        new MetadataItem(22, "iridium_alloy"),
                        new MetadataItem(23, "iron"),
                        new MetadataItem(24, "diamond")
                )
        );
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
        return "nugget";
    }
}
