package com.sabrepenguin.techreborn.items.materials;

import com.sabrepenguin.techreborn.items.MetadataItem;

import java.util.Collection;

public interface IMaterial {
    String getNameFromMeta(int meta);
    Collection<MetadataItem> getItems();
    int getMetaFromName(String name);
    String getOreDict(int meta);
}
