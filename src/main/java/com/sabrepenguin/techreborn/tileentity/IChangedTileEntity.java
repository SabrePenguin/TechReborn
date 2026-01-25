package com.sabrepenguin.techreborn.tileentity;

import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public interface IChangedTileEntity {
	/**
	 * The same warnings about getStackInSlot apply here. DO NOT MODIFY THE ITEMSTACK.
	 */
	void onInputChanged(int slot, ItemStack removedStack, ItemStack addedStack);

}
