package com.sabrepenguin.techreborn.datagen.recipes;

import com.sabrepenguin.techreborn.blocks.TRBlocks;
import com.sabrepenguin.techreborn.datagen.builders.ingredients.ReplaceableIngredient;
import net.minecraft.item.ItemStack;

public enum IC2Classic {;

	final ItemStack trStack;
	final ItemStack ic2Stack;

	IC2Classic(ItemStack stack, ItemStack ic2stack) {
		trStack = stack;
		this.ic2Stack = ic2stack;
	}

	public ReplaceableIngredient getIngredient() {
		return new ReplaceableIngredient(this.trStack, this.ic2Stack);
	}
}
