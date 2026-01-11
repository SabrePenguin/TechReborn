package com.sabrepenguin.techreborn.items.materials;

import com.sabrepenguin.techreborn.items.MetadataItem;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class Gem implements IMaterial {

    private static final List<MetadataItem> ORDERED_ITEMS = new ArrayList<>();
    private static final Int2ObjectMap<MetadataItem> META = new Int2ObjectOpenHashMap<>();
    // I'll switch later if Sai is fine without metadata
    static {
        ORDERED_ITEMS.addAll(
                Arrays.asList(
                        new MetadataItem(0, "ruby"),
                        new MetadataItem(1, "sapphire"),
                        new MetadataItem(2, "peridot"),
                        new MetadataItem(3, "red_garnet"),
                        new MetadataItem(4, "yellow_garnet")
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
        return "gem";
    }
}
