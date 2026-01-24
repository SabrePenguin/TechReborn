package com.sabrepenguin.techreborn.util;

import com.github.bsideup.jabel.Desugar;
import net.minecraft.item.ItemStack;

import java.util.Objects;

@Desugar
public record ItemStackWrapper(ItemStack item) {

	@Override
	public int hashCode() {
		return Objects.hash(item.getItem(), item.getItemDamage(), item.getTagCompound());
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof ItemStackWrapper other) {
			return this.item.isItemEqual(other.item) &&
					ItemStack.areItemStackTagsEqual(this.item, other.item);
		}
		return false;
	}
}