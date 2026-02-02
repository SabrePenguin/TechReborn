package com.sabrepenguin.techreborn.itemblock;

import com.sabrepenguin.techreborn.blocks.IBurnable;
import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

public class ItemBlockBurnable extends ItemBlock {
	private final int burnTime;
	public ItemBlockBurnable(Block block, IBurnable burnable) {
		super(block);
		this.burnTime = burnable.getBurnTime();
	}

	@Override
	public int getItemBurnTime(ItemStack itemStack) {
		return burnTime;
	}
}
