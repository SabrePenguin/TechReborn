package com.sabrepenguin.techreborn.items.materials;

import com.sabrepenguin.techreborn.items.MetadataItem;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Material {
    protected static final List<MetadataItem> ORDERED_ITEMS = new ArrayList<>();
    protected static final Int2ObjectMap<MetadataItem> META = new Int2ObjectOpenHashMap<>();

    public Collection<MetadataItem> getItems() {
        return META.values();
    }

    public List<MetadataItem> getOrderedItems() {
        return ORDERED_ITEMS;
    }

    public String getNameFromMeta(int meta) {
        if (META.containsKey(meta)) {
            return META.get(meta).name();
        }
        return "";
    }

    public int getMetaFromName(String name) {
        for(MetadataItem item: META.values()) {
            if (item.name().equals(name))
                return item.meta();
        }
        return 0;
    }

    public String getOreDict(int meta) {
        return "";
    }
}
