package com.sabrepenguin.techreborn.itemblock;

import net.minecraft.item.ItemStack;
import org.apache.commons.lang3.tuple.Pair;

import java.util.List;

public interface IMetaInformation {
    default String getOreDict() {return "";}
    default boolean hasNonStandardOreDict() {
        return false;
    };
    default String[] getNonStandardOreDict(int meta) {
        return new String[]{};
    }
	String getName(ItemStack stack);
	List<Pair<String, Integer>> getMeta();
}
