package com.sabrepenguin.techreborn.items.tools;

import com.sabrepenguin.techreborn.items.ItemHelper;
import com.sabrepenguin.techreborn.util.INonStandardLocation;
import net.minecraft.item.Item;

public class ItemTreeTap extends Item implements INonStandardLocation {
	public ItemTreeTap() {
		ItemHelper.registerUnstackable(this, "treetap");
		this.setMaxDamage(20);
	}

	@Override
	public String getPrefix() {
		return "tool";
	}
}
