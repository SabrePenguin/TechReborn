package com.sabrepenguin.techreborn.items;

import com.sabrepenguin.techreborn.Tags;
import com.sabrepenguin.techreborn.TechReborn;
import net.minecraft.item.Item;

public class ItemHelper {

	public static void registerItem(Item item, String name) {
		item.setRegistryName(Tags.MODID, name);
		item.setTranslationKey(Tags.MODID + "." + name);
		item.setCreativeTab(TechReborn.RESOURCE_TAB);
	}

	public static void registerUnstackable(Item item, String name) {
		registerItem(item, name);
		item.setMaxStackSize(1);
	}
}
