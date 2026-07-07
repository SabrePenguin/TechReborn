package com.sabrepenguin.techreborn.util;

import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityFurnace;

@FunctionalInterface
public interface ObjIntPredicate<T> {
	boolean test(T t, int value);

	static ObjIntPredicate<ItemStack> fuel(int checkSlot) {
		return ((stack, slot) -> {
			if (slot != checkSlot)
				return true;
			return TileEntityFurnace.getItemBurnTime(stack) > 0;
		});
	}
}