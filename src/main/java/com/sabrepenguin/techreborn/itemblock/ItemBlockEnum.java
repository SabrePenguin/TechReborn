package com.sabrepenguin.techreborn.itemblock;

import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

public class ItemBlockEnum extends ItemBlock {
	public ItemBlockEnum(Block block) {
		super(block);
		this.setMaxDamage(0);
		this.setHasSubtypes(true);
	}

	@Override
	public int getMetadata(int damage) {
		return damage;
	}

	@Override
	public String getTranslationKey(ItemStack stack) {
		if (this.block instanceof IEnumMeta metaBlock) {
			return super.getTranslationKey() + "." + metaBlock.getName(stack);
		}
		return super.getTranslationKey(stack);
	}
}
