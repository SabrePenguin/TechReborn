package com.sabrepenguin.techreborn.items.materials;

import com.sabrepenguin.techreborn.itemblock.IEnumMeta;

public interface IMetaMaterial extends IEnumMeta {
//    String getNameFromMeta(int meta);
//    int getMetaFromName(String name);
    String getOreDict();
    default boolean hasNonStandardOreDict() {
        return false;
    };
    default String[] getNonStandardOreDict(int meta) {
        return new String[]{};
    }
}
