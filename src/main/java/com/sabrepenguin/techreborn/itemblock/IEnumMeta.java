package com.sabrepenguin.techreborn.itemblock;

import net.minecraft.item.ItemStack;
import org.apache.commons.lang3.tuple.Pair;

import java.util.List;

public interface IEnumMeta {
	String getName(ItemStack stack);
	List<Pair<String, Integer>> getMeta();
}
