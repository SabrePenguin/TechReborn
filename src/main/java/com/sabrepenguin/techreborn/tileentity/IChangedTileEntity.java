package com.sabrepenguin.techreborn.tileentity;

import net.minecraft.item.ItemStack;

public interface IChangedTileEntity {
	/**
	 * The same warnings about getStackInSlot apply here. DO NOT MODIFY THE ITEMSTACK.
	 */
	default void onInputChanged(int slot, ItemStack removedStack, ItemStack addedStack) {};

	/**
	 * A smaller method to ensure items added to a stack simply update the TE if it cares
	 */
	default void onSlotCountChanged(int slot) {};
}
