package com.sabrepenguin.techreborn.recipe.utils;

import com.sabrepenguin.techreborn.util.ItemStackWrapper;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

import java.util.Objects;

public class CountedIngredient {

	private final int count;
	private String oreDict;
	private ItemStack itemStack;

	public CountedIngredient(int count, ItemStack stack) {
		this.count = count;
		this.itemStack = stack;
	}

	public CountedIngredient(int count, String oreDict) {
		this.count = count;
		this.oreDict = oreDict;
		if (!OreDictionary.doesOreNameExist(oreDict)) {
			throw new RuntimeException("Could not create ore dictionary counted ingredient");
		}
	}

	public boolean matches(ItemStack stack) {
		if (stack.isEmpty()) return false;
		if (stack.getCount() < count) return false;
		if (oreDict != null) {
			return OreDictionary.containsMatch(false, OreDictionary.getOres(oreDict), stack);
		} else {
			return ItemStack.areItemsEqual(itemStack, stack);
		}
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof CountedIngredient other) {
			if (this.oreDict != null && other.oreDict != null)
				return this.count == other.count && this.oreDict.equals(other.oreDict);
			if (this.itemStack != null && other.itemStack != null)
				return this.count == other.count && this.itemStack.isItemEqual(other.itemStack);
		}
		return false;
	}

	@Override
	public int hashCode() {
		if (this.oreDict != null) {
			return Objects.hash(count, oreDict);
		}
		if (this.itemStack != null) {
			return Objects.hash(count, new ItemStackWrapper(this.itemStack));
		}
		throw new RuntimeException("Unable to hash item, neither oredict or itemstack set");
	}
}
