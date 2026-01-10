package com.sabrepenguin.techreborn.items.materials;

import com.sabrepenguin.techreborn.items.MetadataItem;

import java.util.Collection;
import java.util.List;

public interface IMaterial {
    String getNameFromMeta(int meta);
    Collection<MetadataItem> getItems();
    int getMetaFromName(String name);
    List<MetadataItem> getOrderedItems();
    String getOreDict(int meta);
}
