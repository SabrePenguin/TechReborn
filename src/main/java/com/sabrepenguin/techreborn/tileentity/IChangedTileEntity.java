package com.sabrepenguin.techreborn.tileentity;

import net.minecraft.item.ItemStack;

public interface IChangedTileEntity {
	/**
	 * The same warnings about getStackInSlot apply here. DO NOT MODIFY THE ITEMSTACK.
	 */
	void onInputChanged(int slot, ItemStack stack);
}
